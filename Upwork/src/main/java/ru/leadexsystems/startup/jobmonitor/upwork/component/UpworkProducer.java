package ru.leadexsystems.startup.jobmonitor.upwork.component;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import ru.leadexsystems.startup.jobmonitor.common.criteria.*;
import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.common.util.DateManager;
import ru.leadexsystems.startup.jobmonitor.upwork.component.api.ClientBuilder;
import ru.leadexsystems.startup.jobmonitor.upwork.component.api.SearchJobs;

import java.util.Date;
import java.util.List;

/**
 * Created by Алексей on 31.10.2016.
 */
public class UpworkProducer extends DefaultProducer  {

    private SearchJobs searchJobs;
    private DateManager manager;

    public UpworkProducer(Endpoint endpoint, String key, String secret, String token, String tokenSecret, int start, int limit, DateManager manager) throws NotFoundParameterException {
        super(endpoint);
        this.manager = manager;
        ClientBuilder builder = new ClientBuilder(key, secret, token, tokenSecret, manager);
        searchJobs = new SearchJobs(builder, start, limit);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String query = exchange.getIn().getBody(String.class);

        //fill message
        //TODO pull jobs posted only after lastDate
        DateCriteria criteriaDate = new DateCriteria(getLastDate(query));
        List<Job> answerUpwork = searchJobs.byKeyword(query);
        List<Job> filtered = criteriaDate.meetCriteria(answerUpwork);

        if (filtered.size() > 0){
            Date lastDate = filtered.get(0).getCreateDate();
            setLastDate(lastDate, query);
        }

        exchange.getOut().setBody(filtered);
    }

    private Date getLastDate(String query) throws NotFoundParameterException {
        return manager.getLastDate(query, JobSource.upwork);
    }

    private void setLastDate(Date date, String query) throws NotFoundParameterException {
        manager.setLastDate(date, query, JobSource.upwork);
    }
}
