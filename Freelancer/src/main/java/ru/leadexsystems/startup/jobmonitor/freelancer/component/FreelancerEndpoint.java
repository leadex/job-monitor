package ru.leadexsystems.startup.jobmonitor.freelancer.component;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;
import ru.leadexsystems.startup.jobmonitor.common.util.DateManager;

/**
 * Created by alexar.
 */
public class FreelancerEndpoint extends DefaultEndpoint {

    @UriParam(name = "start")
    private int start;

    @UriParam(name = "limit")
    private int limit;

    @UriParam(name = "apiSearchUrl")
    private String apiSearchUrl;

    DateManager dateManager;

    public FreelancerEndpoint(DateManager dateManager) {
        super();
        this.dateManager = dateManager;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new FreelancerProducer(this, apiSearchUrl, start, limit, dateManager);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new IllegalStateException("Consumer not allowed in this component");
    }

    @Override
    protected String createEndpointUri() {
        return "freelancer://camel";
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getApiSearchUrl() {
        return apiSearchUrl;
    }

    public void setApiSearchUrl(String apiSearchUrl) {
        this.apiSearchUrl = apiSearchUrl;
    }

    public DateManager getDateManager() {
        return dateManager;
    }

    public void setDateManager(DateManager dateManager) {
        this.dateManager = dateManager;
    }
}
