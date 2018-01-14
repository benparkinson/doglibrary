package parkinsonbenjamin.doglibrary.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    private final String host;
    private final int port;
    private final String dbName;
    private final String username;
    private final String password;

    public DbConnectionFactory(String host, int port, String dbName, String username, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
        return DriverManager.getConnection(url, username, password);
    }

}
