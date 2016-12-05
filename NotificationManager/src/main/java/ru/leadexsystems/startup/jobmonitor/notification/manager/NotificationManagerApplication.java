package ru.leadexsystems.startup.jobmonitor.notification.manager;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.leadexsystems.startup.jobmonitor.common.model.SearchResponse;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.common.util.DbManager;
import ru.leadexsystems.startup.jobmonitor.notification.manager.processor.AggregateUsersProcessor;
import ru.leadexsystems.startup.jobmonitor.notification.manager.processor.ModifyTypeProcessor;

/**
 * Created by ekabardinsky on 10/25/16.
 */
@SpringBootApplication
public class NotificationManagerApplication {

    @Value("${activemq.host}")
    private String activemqHost;

    @Value("${activemq.port}")
    private String activemqPort;

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private Integer port;

    @Value("${mongo.db}")
    private String db;

    @Value("${mongo.login}")
    private String login;

    @Value("${mongo.password}")
    private String password;

    @Bean
    public ActiveMQComponent activemq() {
        return ActiveMQComponent
                .activeMQComponent("tcp://" + activemqHost + ":" + activemqPort);
    }

    @Bean
    public PojoToJsonProcessor pojoToJsonProcessor() {
        return new PojoToJsonProcessor();
    }

    @Bean
    public ModifyTypeProcessor modifyTypeProcessor() {
        return new ModifyTypeProcessor();
    }


    @Bean
    public JsonToPojoProcessor taskToPojoProcessor() {
        return new JsonToPojoProcessor(Task.class);
    }

    @Bean
    public DbManager dbManager() {
        return new DbManager(host, port, db, login, password);
    }

    @Bean
    public AggregateUsersProcessor aggregateUsersProcessor(DbManager manager) {
        return new AggregateUsersProcessor(manager);
    }

    @Bean
    public JsonToPojoProcessor jsonToResponseProcessor() {
        return new JsonToPojoProcessor(SearchResponse.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationManagerApplication.class, args);
    }
}
