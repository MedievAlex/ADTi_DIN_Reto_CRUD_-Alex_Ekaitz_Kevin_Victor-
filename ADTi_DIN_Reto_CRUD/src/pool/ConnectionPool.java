package pool;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool
{

    // Pool unique instance
    private static final BasicDataSource DATASOURCE;

    // Pool static configuration
    static
    {
        DATASOURCE = new BasicDataSource();

        // Import configuration from classConfig.properties file
        ResourceBundle configFile = ResourceBundle.getBundle("config.classConfig");

        DATASOURCE.setUrl(configFile.getString("Conn"));
        DATASOURCE.setUsername(configFile.getString("DBUser"));
        DATASOURCE.setPassword(configFile.getString("DBPass"));
        DATASOURCE.setDriverClassName(configFile.getString("Driver"));

        // Pool parameters
        DATASOURCE.setInitialSize(1);      // Start connections
        DATASOURCE.setMaxTotal(2);        // Max total connextions
        DATASOURCE.setMinIdle(1);          // Min inactive connections
        DATASOURCE.setMaxIdle(2);          // Max inactive connections
        DATASOURCE.setMaxWaitMillis(10000); // Max wait time for a connection
    }

    // Method to obtain a connection
    public static Connection getConnection() throws SQLException
    {
        return DATASOURCE.getConnection();
    }
}
