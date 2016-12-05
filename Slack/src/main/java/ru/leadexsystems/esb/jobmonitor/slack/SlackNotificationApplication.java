package ru.leadexsystems.esb.jobmonitor.slack;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.slack.SlackComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.leadexsystems.esb.jobmonitor.slack.processor.AggregateJobProcessor;
import ru.leadexsystems.esb.jobmonitor.slack.processor.JobToSlackMessageProcessor;
import ru.leadexsystems.startup.jobmonitor.common.model.Notification;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;

/**
 * Created by ekabardinsky on 11/1/16.
 */
@SpringBootApplication
public class SlackNotificationApplication {

    @Value("${activemq.host}")
    private String activemqHost;

    @Value("${activemq.port}")
    private String activemqPort;

    @Bean
    public SlackComponent slack() {
        return new SlackComponent();
    }

    @Bean
    public ActiveMQComponent activemq() {
        return ActiveMQComponent
                .activeMQComponent("tcp://" + activemqHost + ":" + activemqPort);
    }

    @Bean
    public JobToSlackMessageProcessor jobToStringFormatter() {
        return new JobToSlackMessageProcessor();
    }

    @Bean
    public AggregateJobProcessor aggregateJobProcessor() {
        return new AggregateJobProcessor();
    }


    @Bean
    public JsonToPojoProcessor jsonToPojoProcessor() {
        return new JsonToPojoProcessor(Notification.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(SlackNotificationApplication.class, args);
    }
}