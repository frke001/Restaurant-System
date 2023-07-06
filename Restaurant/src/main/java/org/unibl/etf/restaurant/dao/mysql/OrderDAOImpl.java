package org.unibl.etf.restaurant.dao.mysql;

import org.unibl.etf.restaurant.dao.MenuDAO;
import org.unibl.etf.restaurant.dao.OrderDAO;
import org.unibl.etf.restaurant.orders.Order;
import org.unibl.etf.restaurant.orders.OrderItem;
import org.unibl.etf.restaurant.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private MenuDAO menuDAO = new MenuDAOImpl();

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_narudžbe()}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                orders.add(new Order(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getBoolean(3),
                        resultSet.getInt(4),resultSet.getString(5)));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return orders;
    }

    @Override
    public List<OrderItem> getOrderItems(Integer orderNumber) {
        List<OrderItem> orderItems = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_stavke(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,orderNumber);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                OrderItem item;
                int value = resultSet.getInt(4);
                if(resultSet.wasNull()){
                    item = new OrderItem(resultSet.getInt(1), resultSet.getInt(2),
                            resultSet.getInt(3),resultSet.getInt(5),menuDAO.getDrinkNameById(resultSet.getInt(5)));
                }else{
                    item = new OrderItem(resultSet.getInt(1), resultSet.getInt(2),
                            resultSet.getInt(3),resultSet.getInt(4),menuDAO.getFoodNameById(resultSet.getInt(4)));
                }
                orderItems.add(item);
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return orderItems;
    }

    @Override
    public Integer addOrder(Order order) {
        Integer result = null;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String procedureCall = "{call dodaj_narudžbu(?,?,?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setBoolean(1,order.isProcessed());
            callableStatement.setInt(2,order.getTableId());
            callableStatement.setString(3, order.getWaiterJMB());
            callableStatement.registerOutParameter(4,Types.INTEGER);

            callableStatement.execute();

            result = callableStatement.getInt(4);

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean addOrderItem(OrderItem orderItem) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String insertOrderItemFoodCall = "{call dodaj_stavku_narudžbe_jelo(?,?,?)}";
        String insertOrderItemDrinkCall = "{call dodaj_stavku_narudžbe_piće(?,?,?)}";
        String getFoodIdCall = "{call dobavi_id_jela(?,?)}";
        String getDrinkIdCall = "{call dobavi_id_pića(?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(getFoodIdCall);
            callableStatement.setString(1,orderItem.getItemName());
            callableStatement.registerOutParameter(2,Types.INTEGER);

            callableStatement.execute();
            Integer itemId;
            itemId = callableStatement.getInt(2);

            if(!callableStatement.wasNull()){
                DBUtil.close(callableStatement);
                callableStatement = connection.prepareCall(insertOrderItemFoodCall);
                callableStatement.setInt(1,orderItem.getQuantity());
                callableStatement.setInt(2,orderItem.getOrderId());
                callableStatement.setInt(3,itemId);

                result = callableStatement.executeUpdate() == 1;
                DBUtil.close(callableStatement);
            }else{
                callableStatement = connection.prepareCall(getDrinkIdCall);
                callableStatement.setString(1,orderItem.getItemName());
                callableStatement.registerOutParameter(2,Types.INTEGER);
                callableStatement.execute();

                itemId = callableStatement.getInt(2);

                DBUtil.close(callableStatement);
                callableStatement = connection.prepareCall(insertOrderItemDrinkCall);
                callableStatement.setInt(1,orderItem.getQuantity());
                callableStatement.setInt(2,orderItem.getOrderId());
                callableStatement.setInt(3,itemId);

                result = callableStatement.executeUpdate() == 1;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return false;
    }

    @Override
    public Order getOrderById(Integer id) {
        Order result = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_narudžbu(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,id);

            resultSet = callableStatement.executeQuery();
            while(resultSet.next())
                result = new Order(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getBoolean(3),
                    resultSet.getInt(4),resultSet.getString(5));

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean deleteOrder(Order order) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String procedureCall = "{call obriši_narudžbu(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1, order.getId());

            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean updateOrder(Order order, boolean type) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String procedureCall = "{call procesiraj_narudžbu(?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1, order.getId());
            callableStatement.setBoolean(2,type);

            result = callableStatement.executeUpdate() == 1;


        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }
}
