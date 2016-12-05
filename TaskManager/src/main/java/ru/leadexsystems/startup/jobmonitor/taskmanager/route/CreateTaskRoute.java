package ru.leadexsystems.startup.jobmonitor.taskmanager.route;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.taskmanager.process.CreateTaskProcessor;
import ru.leadexsystems.startup.jobmonitor.taskmanager.process.KeywordsFetchingProcessor;

/**
 * @author Maxim Ivanov
 */
@Component
public class CreateTaskRoute extends RouteBuilder {

  @Autowired
  KeywordsFetchingProcessor keywordsFetchingProcessor;

  @Autowired
  CreateTaskProcessor createTaskProcessor;

  @Override
  public void configure() throws Exception {
    from("quartz2://taskcreator?cron={{taskManager.createTasksCron}}")
        .process(keywordsFetchingProcessor)
        .split(body())
        .process(createTaskProcessor)
        .to("direct:pushTask");

    from("direct:pushTask")
        .process(new PojoToJsonProcessor())
        .to("activemq:queue://notValidQueue" +
            "?username={{activemq.user}}" +
            "&password={{activemq.password}}" +
            "&disableReplyTo=true");
  }
}
