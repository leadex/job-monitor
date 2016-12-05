package ru.leadexsystems.startup.jobmonitor.notification.manager.model;

import org.bson.Document;
import org.eclipse.jetty.util.Scanner;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;
import ru.leadexsystems.startup.jobmonitor.common.model.NotificationType;
import ru.leadexsystems.startup.jobmonitor.common.model.SearchResponse;

/**
 * Created by ekabardinsky on 11/21/16.
 */
public class NotificationTemplate {
    private SearchResponse response;
    private Client client;
    private NotificationType type;

    public SearchResponse getResponse() {
        return response;
    }

    public void setResponse(SearchResponse response) {
        this.response = response;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
