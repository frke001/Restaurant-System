package org.unibl.etf.restaurant.dao.mysql;

import org.unibl.etf.restaurant.dao.MenuDAO;
import org.unibl.etf.restaurant.menu.Drink;
import org.unibl.etf.restaurant.menu.Food;
import org.unibl.etf.restaurant.menu.Item;
import org.unibl.etf.restaurant.menu.Menu;
import org.unibl.etf.restaurant.util.DBUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {


    @Override
    public List<Item> getItems(Integer menuId) {
        ArrayList<Item> result = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_stavke_menija(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,menuId);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                result.add(new Item(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getDouble(3),resultSet.getString(4)));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean addItem(Item item, Integer menuId) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        try{
            connection = DBUtil.getConnection();
            if(item instanceof Food){
                Food food  = (Food) item;
                String procedureCall = "{call dodaj_jelo(?, ?, ?, ?, ?, ?)}";
                callableStatement = connection.prepareCall(procedureCall);
                callableStatement.setInt(1,food.getId());
                callableStatement.setString(2, food.getName());
                callableStatement.setDouble(3,food.getPrice());
                callableStatement.setString(4,food.getDescription());
                callableStatement.setBoolean(5,food.isFast());
                callableStatement.setInt(6,menuId);
                result = callableStatement.executeUpdate() == 1; // ako je uticalo na jedan red
            }
            else if(item instanceof Drink){
                Drink drink = (Drink) item;
                String procedureCall = "{call dodaj_piće(?, ?, ?, ?, ?, ?)}";
                callableStatement = connection.prepareCall(procedureCall);
                callableStatement.setInt(1,drink.getId());
                callableStatement.setString(2, drink.getName());
                callableStatement.setDouble(3,drink.getPrice());
                callableStatement.setString(4,drink.getDescription());
                callableStatement.setBoolean(5,drink.isAlcoholic());
                callableStatement.setInt(6,menuId);
                result = callableStatement.executeUpdate() == 1; // ako je uticalo na jedan red
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean deleteItem(Item item, Integer menuId, boolean type) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;
        try{
            connection = DBUtil.getConnection();
            String procedureCall = null;
            if(type){
                procedureCall = "{call obriši_jelo(?, ?)}";
            }else{
                procedureCall = "{call obriši_piće(?, ?)}";
            }
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,item.getId());
            callableStatement.setInt(2,menuId);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean updateItem(Item item, Integer menuId,boolean type) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;
        try{
            connection = DBUtil.getConnection();
            String procedureCall = null;
            if(type)
               procedureCall = "{call ažuriraj_jelo(?, ?, ?, ?, ?)}";
            else
                procedureCall = "{call ažuriraj_piće(?, ?, ?, ?, ?)}";

            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,item.getId());
            callableStatement.setInt(2,menuId);
            callableStatement.setString(3,item.getName());
            callableStatement.setDouble(4,item.getPrice());
            String temp = item.getDescription();
            String description = temp.replaceAll(" \\(.*?\\)", "");
            callableStatement.setString(5,description);
            result = callableStatement.executeUpdate() == 1;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public String getFoodNameById(Integer foodId) {
        String result = "";
        Connection connection = null;
        CallableStatement callableStatement = null;


        String procedureCall = "{call dobavi_jelo_ime(?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,foodId);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.execute();
            result = callableStatement.getString(2);


        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public String getDrinkNameById(Integer drinkId) {
        String result = "";
        Connection connection = null;
        CallableStatement callableStatement = null;


        String procedureCall = "{call dobavi_piće_ime(?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,drinkId);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.execute();
            result = callableStatement.getString(2);


        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }
}
