package ru.leadexsystems.startup.jobmonitor.taskmanager;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Maxim Ivanov
 */
@SpringBootApplication
public class TaskManagerApplication {
  @Value("${activemq.host}")
  private String activemqHost;
  @Value("${activemq.port}")
  private String activemqPort;

  @Bean
  public ActiveMQComponent activemq() {
    return ActiveMQComponent
        .activeMQComponent("tcp://" + activemqHost + ":" + activemqPort);
  }

  public static void main(String[] args) {
    SpringApplication.run(TaskManagerApplication.class, args);
  }
}
