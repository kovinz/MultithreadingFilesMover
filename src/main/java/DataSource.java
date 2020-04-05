import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.ConnectionConfig;
import config.HikariParamConfig;
import config.MainConfig;
import config.ThreadConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariDataSource ds;

    DataSource(MainConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        HikariParamConfig hikariParamConfig = config.getHikariConfig();
        ConnectionConfig connectionConfig = config.getConnectionConfig();
        ThreadConfig threadConfig = config.getThreadConfig();
        hikariConfig.setJdbcUrl(connectionConfig.getJdbcUrl());
        hikariConfig.setUsername(connectionConfig.getUsername());
        hikariConfig.setPassword(connectionConfig.getPassword());
        hikariConfig.setDriverClassName(connectionConfig.getDriverClassName());
        hikariConfig.setConnectionTestQuery(hikariParamConfig.getConnectionTestQuery());
        hikariConfig.addDataSourceProperty("cachePrepStmts", hikariParamConfig.getCachePrepStmts());
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", hikariParamConfig.getPrepStmtCacheSize());
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", hikariParamConfig.getPrepStmtCacheSqlLimit());
        hikariConfig.addDataSourceProperty("maximumPoolSize", threadConfig.getQuantityOfThreads());
        ds = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}