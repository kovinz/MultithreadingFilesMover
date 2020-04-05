package config;

public class MainConfig {
    private ConnectionConfig connectionConfig;
    private HikariParamConfig hikariConfig;
    private ThreadConfig threadConfig;
    private QueryConfig queryConfig;
    private DirectoryConfig directoryConfig;

    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public HikariParamConfig getHikariConfig() {
        return hikariConfig;
    }

    public void setHikariConfig(HikariParamConfig hikariConfig) {
        this.hikariConfig = hikariConfig;
    }

    public ThreadConfig getThreadConfig() {
        return threadConfig;
    }

    public void setThreadConfig(ThreadConfig threadConfig) {
        this.threadConfig = threadConfig;
    }

    public QueryConfig getQueryConfig() {
        return queryConfig;
    }

    public void setQueryConfig(QueryConfig queryConfig) {
        this.queryConfig = queryConfig;
    }

    public DirectoryConfig getDirectoryConfig() {
        return directoryConfig;
    }

    public void setDirectoryConfig(DirectoryConfig directoryConfig) {
        this.directoryConfig = directoryConfig;
    }
}
