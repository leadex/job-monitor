package ru.leadexsystems.startup.jobmonitor.taskmanager.model;

import ru.leadexsystems.startup.jobmonitor.common.model.SearchRequest;

/**
 * Created by Maxim Ivanov.
 */
public class SearchRequestTask {
  private int delay;
  private SearchRequest searchRequest;

  public SearchRequestTask(int delay, SearchRequest searchRequest) {
    this.delay = delay;
    this.searchRequest = searchRequest;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public SearchRequest getSearchRequest() {
    return searchRequest;
  }

  public void setSearchRequest(SearchRequest searchRequest) {
    this.searchRequest = searchRequest;
  }
}
