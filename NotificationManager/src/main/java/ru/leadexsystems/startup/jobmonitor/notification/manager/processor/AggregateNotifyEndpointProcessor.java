package ru.leadexsystems.startup.jobmonitor.notification.manager.processor;

import com.google.gson.Gson;
import org.apache.camel.Attachment;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;
import ru.leadexsystems.startup.jobmonitor.common.model.*;
import ru.leadexsystems.startup.jobmonitor.notification.manager.model.NotificationTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ekabardinsky on 11/21/16.
 */
public class AggregateNotifyEndpointProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<Notification> result = new ArrayList<>();

        NotificationTemplate notificationTemplate = exchange.getIn().getBody(NotificationTemplate.class);
        NotificationParams notificationParams = notificationTemplate.getClient().getNotification();
        Slack slack = notificationParams.getSlack();

        if (slack != null){
            Gson gson = new Gson();
            Notification notification = new Notification();
            notification.setNotificationType(NotificationType.slack);
            notification.setJobs(notificationTemplate.getResponse().getJobs());
            notification.setParams(gson.toJson(slack));
            result.add(notification);
        }
        exchange.getOut().setBody(result);
    }

}
