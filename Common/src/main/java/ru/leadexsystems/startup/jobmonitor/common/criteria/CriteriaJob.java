package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.List;

/**
 * Created by alexar.
 */
public interface CriteriaJob {
    List<Job> meetCriteria(List<Job> jobs);

}
