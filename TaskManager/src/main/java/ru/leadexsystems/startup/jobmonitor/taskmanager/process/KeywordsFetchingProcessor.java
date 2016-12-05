package ru.leadexsystems.startup.jobmonitor.taskmanager.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.common.model.SearchRequest;
import ru.leadexsystems.startup.jobmonitor.taskmanager.model.SearchRequestTask;
import ru.leadexsystems.startup.jobmonitor.taskmanager.utils.MongoDbManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Maxim Ivanov.
 */
@Component
public class KeywordsFetchingProcessor implements Processor {

  @Autowired
  MongoDbManager dbManager;

  @Value("${taskManager.createTasksInterval}")
  int createTasksInterval;

  @Override
  public void process(Exchange exchange) throws Exception {
    ArrayList<SearchRequestTask> result = new ArrayList<>();

    HashMap<JobSource, ArrayList<SearchRequest>> requests = new HashMap<>();
    Arrays.stream(JobSource.values())
        .forEach(jobSource -> requests.put(jobSource, new ArrayList<>()));

    Datastore datastore = dbManager.getDatastore();

    datastore.createQuery(SearchRequest.class).forEach(searchRequest -> {
      requests.get(searchRequest.getSource()).add(searchRequest);
    });

    for (JobSource jobSource : JobSource.values()) {
      ArrayList<SearchRequest> searchRequests = requests.get(jobSource);
      int searchRequestsCount = searchRequests.size();

      if (searchRequestsCount > 0) {
        double updateInterval = createTasksInterval * 1000.0;
        double delayDiff = updateInterval / searchRequestsCount;
        double delay = 0;

        for (SearchRequest req : searchRequests) {
          result.add(new SearchRequestTask((int) delay, req));

          delay += delayDiff;
        }
      }
    }

    exchange.getOut().setBody(result);
  }
}
