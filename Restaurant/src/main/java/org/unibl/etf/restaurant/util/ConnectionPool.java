package org.unibl.etf.restaurant.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {

    private static final String BUNDLE_NAME = "ConnectionPool.properties";

    private String jdbcURL;
    private String username;
    private String password;
    private int preConnectCount;
    private int maxIdleConnections;
    private int maxConnections;

    private int connectCount;
    private List<Connection> usedConnections;
    private List<Connection> freeConnections;

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance; // singleton design pattern
    }

    private ConnectionPool() {

        readConfiguration();
        try {
            freeConnections = new ArrayList<>();
            usedConnections = new ArrayList<>();

            for (int i = 0; i < preConnectCount; i++) {
                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                freeConnections.add(conn);
            }
            connectCount = preConnectCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readConfiguration() {
        Properties properties = new Properties();
        ClassLoader classLoader = ConnectionPool.class.getClassLoader();
        try{
            properties.load(classLoader.getResourceAsStream(BUNDLE_NAME));
            jdbcURL = properties.getProperty("jdbcURL");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        }catch (IOException ex){
            ex.printStackTrace();
        }
        preConnectCount = 0;
        maxIdleConnections = 10;
        maxConnections = 10;
        try {
            preConnectCount = Integer.parseInt(properties.getProperty("preConnectCount"));
            maxIdleConnections = Integer.parseInt(properties.getProperty("maxIdleConnections"));
            maxConnections = Integer.parseInt(properties.getProperty("maxConnections"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection checkOut() throws SQLException {
        Connection conn = null;
        if (freeConnections.size() > 0) {
            conn = freeConnections.remove(0);
            usedConnections.add(conn);
        } else {
            if (connectCount < maxConnections) {
                conn = DriverManager.getConnection(jdbcURL, username, password);
                usedConnections.add(conn);
                connectCount++;
            } else {
                try {
                    wait();
                    conn = freeConnections.remove(0);
                    usedConnections.add(conn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }

    public synchronized void checkIn(Connection conn) {
        if (conn == null)
            return;
        if (usedConnections.remove(conn)) {
            freeConnections.add(conn);
            while (freeConnections.size() > maxIdleConnections) {
                int lastOne = freeConnections.size() - 1;
                Connection c = freeConnections.remove(lastOne);
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            notify();
        }
    }

}

