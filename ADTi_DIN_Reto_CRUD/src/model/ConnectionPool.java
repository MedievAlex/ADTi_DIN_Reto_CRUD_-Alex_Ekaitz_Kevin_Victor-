package model;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {
    // Instancia única del pool
    private static final BasicDataSource dataSource;
    
    // Configuración estática del pool
    static {
        dataSource = new BasicDataSource();
        
        // Cargar configuración desde classConfig.properties
        ResourceBundle configFile = ResourceBundle.getBundle("model.classConfig");
        
        dataSource.setUrl(configFile.getString("Conn"));
        dataSource.setUsername(configFile.getString("DBUser"));
        dataSource.setPassword(configFile.getString("DBPass"));
        dataSource.setDriverClassName(configFile.getString("Driver"));
        
        // Parámetros del pool
        dataSource.setInitialSize(5);      // Conexiones iniciales
        dataSource.setMaxTotal(10);        // Máximo total de conexiones
        dataSource.setMinIdle(2);          // Mínimo de conexiones inactivas
        dataSource.setMaxIdle(5);          // Máximo de conexiones inactivas
        dataSource.setMaxWaitMillis(10000); // Tiempo máximo para esperar una conexión
    }


    // Método para obtener una conexión
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}