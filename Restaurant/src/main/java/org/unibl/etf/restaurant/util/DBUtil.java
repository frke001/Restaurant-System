package org.unibl.etf.restaurant.util;

import java.sql.*;

public class DBUtil {

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    public DBUtil(){
        super();
    }

    public static PreparedStatement prepareStatement(Connection c, String sql,
                                                     boolean retGenKeys, Object... values) throws SQLException {
        PreparedStatement ps = c.prepareStatement(sql,
                retGenKeys ? Statement.RETURN_GENERATED_KEYS
                        : Statement.NO_GENERATED_KEYS);

        for (int i = 0; i < values.length; i++)
            ps.setObject(i + 1, values[i]); // parametri se indeksiraju od 1

        return ps;
    }
//    public static CallableStatement prepareCall(Connection connection, String call, Object... inParams) throws SQLException{
//
//        CallableStatement callableStatement = connection.prepareCall(call);
//
//    }

    public static Connection getConnection() throws SQLException {
        Connection connection = connectionPool.checkOut();
        return connection;
    }

    public static void close(Connection connection) {
        if (connection != null)
            connectionPool.checkIn(connection);
    }

    public static void close(Statement statement) {
        if (statement != null)
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public static void close(CallableStatement callableStatement){
        if (callableStatement != null)
            try {
                callableStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        close(resultSet);
        close(statement);
        close(connection);
    }

    public static void close(ResultSet resultSet, CallableStatement callableStatement, Connection connection){
        close(resultSet);
        close(callableStatement);
        close(connection);
    }

    public static void close(Statement statement, Connection connection) {
        close(statement);
        close(connection);
    }
}