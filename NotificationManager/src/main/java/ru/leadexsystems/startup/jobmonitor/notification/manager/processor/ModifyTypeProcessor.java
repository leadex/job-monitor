package ru.leadexsystems.startup.jobmonitor.notification.manager.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.leadexsystems.startup.jobmonitor.common.model.Notification;
import ru.leadexsystems.startup.jobmonitor.notification.manager.model.NotificationTemplate;

/**
 * Created by alexar on 24.11.2016.
 */
public class ModifyTypeProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        Notification body = exchange.getIn().getBody(Notification.class);
        exchange.getProperties().put("type", body.getNotificationType());
    }
}
