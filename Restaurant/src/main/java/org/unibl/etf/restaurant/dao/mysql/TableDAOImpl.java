package org.unibl.etf.restaurant.dao.mysql;

import org.unibl.etf.restaurant.dao.TableDAO;
import org.unibl.etf.restaurant.util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDAOImpl implements TableDAO {
    @Override
    public List<Integer> getTableIds(Integer restaurantId) {
        List<Integer> tableIds = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_oznake_stolova(?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,restaurantId);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                tableIds.add(resultSet.getInt(1));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return tableIds;
    }
}
