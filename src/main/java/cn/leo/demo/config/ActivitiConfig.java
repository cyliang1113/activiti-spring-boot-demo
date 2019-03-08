package cn.leo.demo.config;

import cn.leo.demo.activiti.custom.manager.CustomGroupEntityManager;
import cn.leo.demo.activiti.custom.manager.CustomGroupEntityManagerFactory;
import cn.leo.demo.activiti.custom.manager.CustomUserEntityManager;
import cn.leo.demo.activiti.custom.manager.CustomUserEntityManagerFactory;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * activiti配置
 */
@Configuration
public class ActivitiConfig {

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            DataSource dataSource, PlatformTransactionManager transactionManager,
            CustomGroupEntityManagerFactory customGroupEntityManagerFactory,
            CustomUserEntityManagerFactory customUserEntityManagerFactory) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDatabaseType("mysql");
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setTransactionManager(transactionManager);
        List<SessionFactory> customSessionFactories = new ArrayList<SessionFactory>();
        customSessionFactories.add(customGroupEntityManagerFactory);
        customSessionFactories.add(customUserEntityManagerFactory);
        processEngineConfiguration.setCustomSessionFactories(customSessionFactories);
        return processEngineConfiguration;
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine){
        return processEngine.getHistoryService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }


}
