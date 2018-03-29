package com.fortinet.fcasb.watcher.alert.init;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by zliu on 17/7/24.
 */
@Service
@Scope("singleton")
@DisconfFile(filename="mysql.properties")
public class DBProperties {
    private String poolName;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private int hikariMaxPoolSize;
    private long hikariIdleTimeout;
    private String datasourceUrl;
    private String datasourceUserName;
    private String datasourcePassword;
    private int statementCacheSize;
    private int jdbcBatchSize;
    private int dbcpPsMaxIdle;
    private boolean jdbcRapResultSets;
    private String driverClass;
    private String dialect;
    private boolean showSQL;
    private boolean formatSQL;
    private boolean useSqlComments;
    private boolean connectionAutocommit;
    private String connectionReleaseMode;
    private String ddlAuto;


    /**
     * hibernate.hikari.dataSource.cachePrepStmts=true
     hibernate.hikari.dataSource.prepStmtCacheSize=250
     hibernate.hikari.dataSource.prepStmtCacheSqlLimit=2048
     hibernate.hikari.dataSource.useServerPrepStmts=true
     hibernate.hikari.dataSource.useLocalSessionState=true
     hibernate.hikari.dataSource.useLocalTransactionState=true
     hibernate.hikari.dataSource.rewriteBatchedStatements=true
     hibernate.hikari.dataSource.cacheResultSetMetadata=true
     hibernate.hikari.dataSource.cacheServerConfiguration=true
     hibernate.hikari.dataSource.elideSetAutoCommits=true
     hibernate.hikari.dataSource.maintainTimeStats=false
     * @return
     */

    private boolean cachePrepStmts;
    private int prepStmtCacheSize;
    private int prepStmtCacheSqlLimit;
    private boolean useServerPrepStmts;
    private boolean useLocalSessionState;
    private boolean useLocalTransactionState;
    private boolean rewriteBatchedStatements;
    private boolean cacheResultSetMetadata;
    private boolean cacheServerConfiguration;
    private boolean elideSetAutoCommits;
    private boolean maintainTimeStats;
    @DisconfFileItem(name = "hibernate.hikari.dataSource.cachePrepStmts", associateField = "cachePrepStmts")
    public boolean isCachePrepStmts() {
        return cachePrepStmts;
    }
    public void setCachePrepStmts(boolean cachePrepStmts) {
        this.cachePrepStmts = cachePrepStmts;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.prepStmtCacheSize", associateField = "prepStmtCacheSize")
    public int getPrepStmtCacheSize() {
        return prepStmtCacheSize;
    }

    public void setPrepStmtCacheSize(int prepStmtCacheSize) {
        this.prepStmtCacheSize = prepStmtCacheSize;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.prepStmtCacheSqlLimit", associateField = "prepStmtCacheSqlLimit")
    public int getPrepStmtCacheSqlLimit() {
        return prepStmtCacheSqlLimit;
    }

    public void setPrepStmtCacheSqlLimit(int prepStmtCacheSqlLimit) {
        this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.useServerPrepStmts", associateField = "useServerPrepStmts")
    public boolean isUseServerPrepStmts() {
        return useServerPrepStmts;
    }

    public void setUseServerPrepStmts(boolean useServerPrepStmts) {
        this.useServerPrepStmts = useServerPrepStmts;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.useLocalSessionState", associateField = "useLocalSessionState")
    public boolean isUseLocalSessionState() {
        return useLocalSessionState;
    }

    public void setUseLocalSessionState(boolean useLocalSessionState) {
        this.useLocalSessionState = useLocalSessionState;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.useLocalTransactionState", associateField = "useLocalTransactionState")
    public boolean isUseLocalTransactionState() {
        return useLocalTransactionState;
    }

    public void setUseLocalTransactionState(boolean useLocalTransactionState) {
        this.useLocalTransactionState = useLocalTransactionState;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.rewriteBatchedStatements", associateField = "rewriteBatchedStatements")
    public boolean isRewriteBatchedStatements() {
        return rewriteBatchedStatements;
    }

    public void setRewriteBatchedStatements(boolean rewriteBatchedStatements) {
        this.rewriteBatchedStatements = rewriteBatchedStatements;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.cacheResultSetMetadata", associateField = "cacheResultSetMetadata")
    public boolean isCacheResultSetMetadata() {
        return cacheResultSetMetadata;
    }

    public void setCacheResultSetMetadata(boolean cacheResultSetMetadata) {
        this.cacheResultSetMetadata = cacheResultSetMetadata;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.cacheServerConfiguration", associateField = "cacheServerConfiguration")
    public boolean isCacheServerConfiguration() {
        return cacheServerConfiguration;
    }

    public void setCacheServerConfiguration(boolean cacheServerConfiguration) {
        this.cacheServerConfiguration = cacheServerConfiguration;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.elideSetAutoCommits", associateField = "elideSetAutoCommits")
    public boolean isElideSetAutoCommits() {
        return elideSetAutoCommits;
    }

    public void setElideSetAutoCommits(boolean elideSetAutoCommits) {
        this.elideSetAutoCommits = elideSetAutoCommits;
    }
    @DisconfFileItem(name = "hibernate.hikari.dataSource.maintainTimeStats", associateField = "maintainTimeStats")
    public boolean isMaintainTimeStats() {
        return maintainTimeStats;
    }

    public void setMaintainTimeStats(boolean maintainTimeStats) {
        this.maintainTimeStats = maintainTimeStats;
    }

    @DisconfFileItem(name = "hibernate.pool.name", associateField = "poolName")
    public String getPoolName() {
        return poolName;
    }
    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    @DisconfFileItem(name = "hibernate.connection.test.query", associateField = "connectionTestQuery")
    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }
    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    @DisconfFileItem(name = "hibernate.data.source.class.name", associateField = "dataSourceClassName")
    public String getDataSourceClassName() {
        return dataSourceClassName;
    }
    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }

    @DisconfFileItem(name = "hibernate.hikari.maximum.pool.size", associateField = "hikariMaxPoolSize")
    public int getHikariMaxPoolSize() {
        return hikariMaxPoolSize;
    }
    public void setHikariMaxPoolSize(int hikariMaxPoolSize) {
        this.hikariMaxPoolSize = hikariMaxPoolSize;
    }

    @DisconfFileItem(name = "hibernate.hikari.idle.timeout", associateField = "hikariIdleTimeout")
    public long getHikariIdleTimeout() {
        return hikariIdleTimeout;
    }
    public void setHikariIdleTimeout(long hikariIdleTimeout) {
        this.hikariIdleTimeout = hikariIdleTimeout;
    }

    @DisconfFileItem(name = "hibernate.datasource.url", associateField = "datasourceUrl")
    public String getDatasourceUrl() {
        return datasourceUrl;
    }
    public void setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
    }

    @DisconfFileItem(name = "hibernate.datasource.username", associateField = "datasourceUserName")
    public String getDatasourceUserName() {
        return datasourceUserName;
    }
    public void setDatasourceUserName(String datasourceUserName) {
        this.datasourceUserName = datasourceUserName;
    }

    @DisconfFileItem(name = "hibernate.datasource.password", associateField = "datasourcePassword")
    public String getDatasourcePassword() {
        return datasourcePassword;
    }
    public void setDatasourcePassword(String datasourcePassword) {
        this.datasourcePassword = datasourcePassword;
    }

    @DisconfFileItem(name = "hibernate.statement_cache.size", associateField = "statementCacheSize")
    public int getStatementCacheSize() {
        return statementCacheSize;
    }
    public void setStatementCacheSize(int statementCacheSize) {
        this.statementCacheSize = statementCacheSize;
    }

    @DisconfFileItem(name = "hibernate.jdbc.batch_size", associateField = "jdbcBatchSize")
    public int getJdbcBatchSize() {
        return jdbcBatchSize;
    }
    public void setJdbcBatchSize(int jdbcBatchSize) {
        this.jdbcBatchSize = jdbcBatchSize;
    }

    @DisconfFileItem(name = "hibernate.dbcp.ps.maxIdle", associateField = "dbcpPsMaxIdle")
    public int getDbcpPsMaxIdle() {
        return dbcpPsMaxIdle;
    }
    public void setDbcpPsMaxIdle(int dbcpPsMaxIdle) {
        this.dbcpPsMaxIdle = dbcpPsMaxIdle;
    }

    @DisconfFileItem(name = "hibernate.jdbc.rap_result_sets", associateField = "jdbcRapResultSets")
    public boolean isJdbcRapResultSets() {
        return jdbcRapResultSets;
    }
    public void setJdbcRapResultSets(boolean jdbcRapResultSets) {
        this.jdbcRapResultSets = jdbcRapResultSets;
    }

    @DisconfFileItem(name = "hibernate.driver_class", associateField = "driverClass")
    public String getDriverClass() {
        return driverClass;
    }
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @DisconfFileItem(name = "hibernate.dialect", associateField = "dialect")
    public String getDialect() {
        return dialect;
    }
    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    @DisconfFileItem(name = "hibernate.show_sql", associateField = "showSQL")
    public boolean isShowSQL() {
        return showSQL;
    }
    public void setShowSQL(boolean showSQL) {
        this.showSQL = showSQL;
    }

    @DisconfFileItem(name = "hibernate.format_sql", associateField = "formatSQL")
    public boolean isFormatSQL() {
        return formatSQL;
    }
    public void setFormatSQL(boolean formatSQL) {
        this.formatSQL = formatSQL;
    }

    @DisconfFileItem(name = "hibernate.use_sql_comments", associateField = "useSqlComments")
    public boolean isUseSqlComments() {
        return useSqlComments;
    }
    public void setUseSqlComments(boolean useSqlComments) {
        this.useSqlComments = useSqlComments;
    }

    @DisconfFileItem(name = "hibernate.connection.autocommit", associateField = "connectionAutocommit")
    public boolean isConnectionAutocommit() {
        return connectionAutocommit;
    }
    public void setConnectionAutocommit(boolean connectionAutocommit) {
        this.connectionAutocommit = connectionAutocommit;
    }

    @DisconfFileItem(name = "hibernate.connection.release_mode", associateField = "connectionReleaseMode")
    public String getConnectionReleaseMode() {
        return connectionReleaseMode;
    }
    public void setConnectionReleaseMode(String connectionReleaseMode) {
        this.connectionReleaseMode = connectionReleaseMode;
    }
    
    @DisconfFileItem(name = "hibernate.hbm2ddl.auto", associateField = "ddlAuto")
	public String getDdlAuto() {
		return ddlAuto;
	}
	public void setDdlAuto(String ddlAuto) {
		this.ddlAuto = ddlAuto;
	}
}
