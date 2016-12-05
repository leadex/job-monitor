package jobmonitor.backend.api.model;

import ru.leadexsystems.startup.jobmonitor.common.model.Client;
import ru.leadexsystems.startup.jobmonitor.common.model.NotificationParams;
import ru.leadexsystems.startup.jobmonitor.common.model.Query;

/**
 * Created by ekabardinsky on 11/25/16.
 */
public class ClientDTO {
    private Query query;
    private NotificationParams notification;

    public ClientDTO(){}

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public NotificationParams getNotification() {
        return notification;
    }

    public void setNotification(NotificationParams notification) {
        this.notification = notification;
    }
}
