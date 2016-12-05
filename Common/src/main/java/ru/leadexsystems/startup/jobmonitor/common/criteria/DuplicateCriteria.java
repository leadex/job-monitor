package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alexar.
 */
public class DuplicateCriteria implements CriteriaJob {
    @Override
    public List<Job> meetCriteria(List<Job> jobs) {
        List<Job> res = new ArrayList<>();
        Set<String> setUrl = new HashSet<>();
        for (Job cur : jobs){
            if (!setUrl.contains(cur.getUrl())){
                res.add(cur);
                setUrl.add(cur.getUrl());
            }
        }
        return res;
    }
}
