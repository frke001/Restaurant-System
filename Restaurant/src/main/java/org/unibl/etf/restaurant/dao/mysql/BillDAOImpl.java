package org.unibl.etf.restaurant.dao.mysql;

import org.unibl.etf.restaurant.dao.BillDAO;
import org.unibl.etf.restaurant.orders.Bill;
import org.unibl.etf.restaurant.orders.Order;
import org.unibl.etf.restaurant.util.DBUtil;
import org.unibl.etf.restaurant.workers.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {
    @Override
    public List<Bill> getBills() {
        List<Bill> bills = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        String procedureCall = "{call dobavi_račune()}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                bills.add(new Bill(resultSet.getInt(1),resultSet.getTimestamp(2),resultSet.getBoolean(3)));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return bills;
    }

    @Override
    public Bill getBillById(Integer billId) {
        Bill result = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_račun(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,billId);

            resultSet = callableStatement.executeQuery();
            while(resultSet.next())
                result = new Bill(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getBoolean(3));

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }
}
