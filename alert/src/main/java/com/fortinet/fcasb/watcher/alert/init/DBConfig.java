package com.fortinet.fcasb.watcher.alert.init;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author zliu on
 */
@EnableTransactionManagement
@EnableJpaRepositories(
entityManagerFactoryRef = "entityManagerFactoryRef",
transactionManagerRef = "transactionManagerRef",
basePackages = {
        "com.fortinet.fcasb.watcher.alert.repo"
})
@Configuration
public class DBConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConfig.class);

    @Bean(name = "jpaProperties")
    public Properties jpaProperties(@Autowired DBProperties loadProperties){
        LOGGER.debug("#########init jpaProperties");

        /**
         * <prop key="hibernate.statement_cache.size">0</prop>
         <prop key="hibernate.jdbc.batch_size">0</prop>
         <prop key="hibernate.dbcp.ps.maxIdle">0</prop>
         <prop key="hibernate.jdbc.rap_result_sets">true</prop>
         <!--<prop key="hibernate.hbm2ddl.auto">${db.hbm2.ddl.auto}</prop>-->
         <prop key="hibernate.dialect">${db.dialect}</prop>
         <prop key="hibernate.driver_class">${db.driver_class}</prop>
         <prop key="hibernate.show_sql">false</prop>
         <prop key="hibernate.format_sql">false</prop>
         <prop key="hibernate.use_sql_comments">false</prop>
         <prop key="hibernate.connection.autocommit">true</prop>
         <prop key="hibernate.connection.release_mode">after_transaction</prop>
         */
        Properties properties = new Properties();
        properties.setProperty("hibernate.statement_cache.size", String.valueOf(loadProperties.getStatementCacheSize()));
        properties.setProperty("hibernate.jdbc.batch_size", String.valueOf(loadProperties.getJdbcBatchSize()));
        properties.setProperty("hibernate.dbcp.ps.maxIdle", String.valueOf(loadProperties.getDbcpPsMaxIdle()));
        properties.setProperty("hibernate.jdbc.rap_result_sets", String.valueOf(loadProperties.isJdbcRapResultSets()));
        properties.setProperty("hibernate.dialect",loadProperties.getDialect());
        properties.setProperty("hibernate.driver_class",loadProperties.getDriverClass());
        properties.setProperty("hibernate.show_sql", String.valueOf(loadProperties.isShowSQL()));
        properties.setProperty("hibernate.format_sql", String.valueOf(loadProperties.isFormatSQL()));
        properties.setProperty("hibernate.use_sql_comments", String.valueOf(loadProperties.isUseSqlComments()));
        properties.setProperty("hibernate.connection.autocommit", String.valueOf(loadProperties.isConnectionAutocommit()));
        properties.setProperty("hibernate.connection.release_mode",loadProperties.getConnectionReleaseMode());
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        
        properties.setProperty("hibernate.hbm2ddl.auto", loadProperties.getDdlAuto());
        return properties;
    }

    @Bean(name = "hikariConfig")
    public HikariConfig hikariConfig(DBProperties loadProperties) {
        LOGGER.debug("#########init hikariConfig");

        HikariConfig config = new HikariConfig();
//        config.setPoolName(loadProperties.getPoolName());
//        config.setConnectionTestQuery(loadProperties.getConnectionTestQuery());
//        config.setDataSourceClassName(loadProperties.getDataSourceClassName());
//        config.setMaximumPoolSize(loadProperties.getHikariMaxPoolSize());
//        config.setIdleTimeout(loadProperties.getHikariIdleTimeout());
        /**
         * <property name="dataSourceProperties">
         <props>
         <prop key="url">${dataSource.url}</prop>
         <prop key="user">${dataSource.username}</prop>
         <prop key="password">${dataSource.password}</prop>
         </props>
         </property>
         */
        Properties properties = new Properties();
        properties.setProperty("cachePrepStmts",String.valueOf(loadProperties.isCachePrepStmts()));
        properties.setProperty("prepStmtCacheSize",String.valueOf(loadProperties.getPrepStmtCacheSize()));
        properties.setProperty("prepStmtCacheSqlLimit",String.valueOf(loadProperties.getPrepStmtCacheSqlLimit()));
        properties.setProperty("useLocalSessionState",String.valueOf(loadProperties.isUseLocalSessionState()));
        properties.setProperty("useLocalTransactionState",String.valueOf(loadProperties.isUseLocalTransactionState()));
        properties.setProperty("rewriteBatchedStatements",String.valueOf(loadProperties.isRewriteBatchedStatements()));
        properties.setProperty("cacheResultSetMetadata",String.valueOf(loadProperties.isCacheResultSetMetadata()));
        properties.setProperty("cacheServerConfiguration",String.valueOf(loadProperties.isCacheServerConfiguration()));
        properties.setProperty("elideSetAutoCommits",String.valueOf(loadProperties.isElideSetAutoCommits()));
        properties.setProperty("maintainTimeStats",String.valueOf(loadProperties.isMaintainTimeStats()));

        config.setDataSourceProperties(properties);
        config.setJdbcUrl(loadProperties.getDatasourceUrl());
        config.setUsername(loadProperties.getDatasourceUserName());
        config.setPassword(loadProperties.getDatasourcePassword());
        return config;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("hikariConfig") HikariConfig hikariConfig) {
        LOGGER.debug("#########init dataSource");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    @Bean(name="loadTimeWeaver")
    public InstrumentationLoadTimeWeaver loadTimeWeaver(){
        LOGGER.debug("#########init loadTimeWeaver");

        return new InstrumentationLoadTimeWeaver();
    }

    @Bean(name="entityManagerFactoryRef")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRef(
            @Qualifier("dataSource") DataSource datasource,
            @Qualifier("jpaProperties")Properties jpaProperties,
            @Qualifier("loadTimeWeaver")LoadTimeWeaver loadTimeWeaver){
        LOGGER.debug("#########init entityManagerFactory");

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =  new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(datasource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setLoadTimeWeaver(loadTimeWeaver);
        entityManagerFactoryBean.setPackagesToScan(
                "com.fortinet.fcasb.watcher.alert.domain"
                );
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        entityManagerFactoryBean.setJpaVendorAdapter(adapter);
        
        return entityManagerFactoryBean;

    }

    @Bean(name="transactionManagerRef")
    public JpaTransactionManager transactionManagerRef(
            @Qualifier("entityManagerFactoryRef") EntityManagerFactory entityManagerFactoryBean){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean);
        return transactionManager;
    }
}