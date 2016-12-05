package ru.leadexsystems.startup.jobmonitor.freelancer;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.common.util.DbManager;
import ru.leadexsystems.startup.jobmonitor.freelancer.component.FreelancerComponent;
import ru.leadexsystems.startup.jobmonitor.freelancer.processor.JobToSearchResponse;

/**
 * Created by ekabardinsky on 10/25/16.
 */
@SpringBootApplication
public class FreelancerJobMonitorApplication {

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
    public FreelancerComponent freelancer() {
        return new FreelancerComponent(new DbManager(host, port, db, login, password));
    }

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
    public JsonToPojoProcessor taskToPojoProcessor(){
        return new JsonToPojoProcessor(Task.class);
    }

    @Bean
    public JobToSearchResponse jobToSearchResponse() {
        return new JobToSearchResponse();
    }

    public static void main(String[] args) {
        SpringApplication.run(FreelancerJobMonitorApplication.class, args);
    }
}
