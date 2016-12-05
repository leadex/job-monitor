package ru.leadexsystems.startup.jobmonitor.taskmanager.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;
import ru.leadexsystems.startup.jobmonitor.taskmanager.model.SearchRequestTask;

/**
 * Created by Maxim Ivanov.
 */
@Component
public class CreateTaskProcessor implements Processor {

  @Value("${activemq.tasksQueuePrefix}")
  String tasksQueuePrefix;

  @Override
  public void process(Exchange exchange) throws Exception {
    SearchRequestTask searchRequestTask = (SearchRequestTask) exchange.getIn().getBody();

    // Set delay
    exchange.getOut().setHeader("AMQ_SCHEDULED_DELAY", searchRequestTask.getDelay());

    // Set queue
    String queueName = tasksQueuePrefix
        + searchRequestTask.getSearchRequest().getSource().name().toLowerCase();
    exchange.getOut().setHeader("CamelJmsDestinationName", queueName);

    // Return task
    Task task = new Task();
    task.setQuery(searchRequestTask.getSearchRequest().getQuery());
    exchange.getOut().setBody(task);
  }
}
