package ru.leadexsystems.startup.jobmonitor.freelancer.component;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import ru.leadexsystems.startup.jobmonitor.common.util.DateManager;
import java.util.Map;

/**
 * Created by alexar.
 */
public class FreelancerComponent extends DefaultComponent {

    DateManager sourceParameter;

    public FreelancerComponent(DateManager sourceParameter) {
        this.sourceParameter = sourceParameter;
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        return new FreelancerEndpoint(sourceParameter);
    }
}
