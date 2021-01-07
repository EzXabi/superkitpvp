package ezxabi.kitpvp.database;

import ezxabi.kitpvp.config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    //*
    // Ophalen  van config informatie
    //*
    private String database = ConfigManager.getConfig().getString("mysql.name");
    private String host = ConfigManager.getConfig().getString("mysql.host");
    private String username = ConfigManager.getConfig().getString("mysql.username");
    private String password = ConfigManager.getConfig().getString("mysql.password");
    private int port = ConfigManager.getConfig().getInt("mysql.port");


    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    //*
    // Connect database
    //*
    public void connect() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" +
                        host + ":" + port + "/" + database + "?useSSL=false",
                username, password);
    }

    //*
    // Disconnect database
    //*
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }


}
