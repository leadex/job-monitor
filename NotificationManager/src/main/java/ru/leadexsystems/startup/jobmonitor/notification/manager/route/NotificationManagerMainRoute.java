package ru.leadexsystems.startup.jobmonitor.notification.manager.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.model.NotificationType;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.notification.manager.processor.AggregateNotifyEndpointProcessor;
import ru.leadexsystems.startup.jobmonitor.notification.manager.processor.AggregateUsersProcessor;
import ru.leadexsystems.startup.jobmonitor.notification.manager.processor.ModifyTypeProcessor;

/**
 * Created by ekabardinsky on 10/25/16.
 */
@Component
public class NotificationManagerMainRoute extends RouteBuilder {

    @Autowired
    private ModifyTypeProcessor modifyTypeProcessor;

    @Autowired
    private PojoToJsonProcessor pojoToJsonProcessor;

    @Autowired
    private JsonToPojoProcessor jsonToResponseProcessor;

    @Autowired
    private AggregateUsersProcessor aggregateUsersProcessor;

    @Value("${activemq.slackNoteQueue}")
    private String slackNoteQueue;

    @Value("${activemq.responseQueue}")
    private String responseQueue;

    @Value("${activemq.user}")
    private String activemqUser;

    @Value("${activemq.password}")
    private String activemqPass;

    @Override
    public void configure() throws Exception {
        from(activeMQ(responseQueue))
                .to("direct:processResponse");

        from("direct:processResponse")
                .process(jsonToResponseProcessor)
                .process(aggregateUsersProcessor)
                .split(body())
                .process(new AggregateNotifyEndpointProcessor())
                .split(body())
                .process(modifyTypeProcessor)
                .to("direct:notifyEndpointOutput");

        from("direct:notifyEndpointOutput")
                .process(pojoToJsonProcessor)
                .choice()
                    .when(exchangeProperty("type").isEqualTo(NotificationType.slack))
                        .to(activeMQ(slackNoteQueue));
    }

    private String activeMQ(String queue) {
        return "activemq:queue://" + queue +
                "?username=" + activemqUser +
                "&password=" + activemqPass +
                "&disableReplyTo=true";
    }
}
