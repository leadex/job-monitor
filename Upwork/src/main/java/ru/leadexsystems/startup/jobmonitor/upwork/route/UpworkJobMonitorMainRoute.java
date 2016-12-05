package ru.leadexsystems.startup.jobmonitor.upwork.route;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.filter.AcceptFilledCollectionFilter;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.upwork.processor.JobToSearchResponse;

/**
 * Created by ekabardinsky on 10/25/16.
 */
@Component
public class UpworkJobMonitorMainRoute extends RouteBuilder {

    @Autowired
    private PojoToJsonProcessor pojoToJsonProcessor;

    @Autowired
    private JsonToPojoProcessor taskToPojoProcessor;

    @Autowired
    private JobToSearchResponse jobToSearchResponse;

    @Value("${upwork.camelCron}")
    private String cron;

    @Value("${upwork.key}")
    private String key;

    @Value("${upwork.secret}")
    private String secret;

    @Value("${upwork.token}")
    private String token;

    @Value("${upwork.tokenSecret}")
    private String tokenSecret;

    @Value("${upwork.pagination.start}")
    private String start;

    @Value("${upwork.pagination.limit}")
    private String limit;

    @Value("${activemq.taskQueue}")
    private String taskQueue;

    @Value("${activemq.responseQueue}")
    private String responseQueue;

    @Value("${activemq.user}")
    private String activemqUser;

    @Value("${activemq.password}")
    private String activemqPass;

    @Override
    public void configure() throws Exception {
        //listening task queue
        from(activeMQ(taskQueue))
                .to("direct:pullUpwork");

        //pull from upwork
        from("direct:pullUpwork")
                .process(taskToPojoProcessor)
                .setProperty("query", body())
                .process(taskToQuery())
                .to(upwork())
                .filter().method(AcceptFilledCollectionFilter.class, "isFilledResponse")
                .process(jobToSearchResponse)
                .to("direct:output");

        //send message to response queue
        from("direct:output")
                .process(pojoToJsonProcessor)
                .to(activeMQ(responseQueue));

    }

    private String activeMQ(String queue) {
        return "activemq:queue://" + queue +
                "?username=" + activemqUser +
                "&password=" + activemqPass +
                "&disableReplyTo=true";
    }

    private String upwork() {
        return "upwork://something" +
                "?key=" + key +
                "&secret=" + secret +
                "&token=" + token +
                "&tokenSecret=" + tokenSecret +
                "&start=" + start +
                "&limit=" + limit;
    }

    private Processor taskToQuery() {
        return (exchange -> {
            Task body = exchange.getIn().getBody(Task.class);
            exchange.getOut().setBody(body.getQuery());
        });
    }
}
