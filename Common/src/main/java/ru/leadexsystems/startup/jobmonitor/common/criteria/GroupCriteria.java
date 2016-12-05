package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 10.11.2016.
 */
public class GroupCriteria {

    private List<CriteriaJob> criterias;

    public GroupCriteria() {
        criterias = new ArrayList<>();
    }

    public GroupCriteria(List<CriteriaJob> criterias) {
        this.criterias = criterias;
    }

    public void addCriteria(CriteriaJob criteria){
        criterias.add(criteria);
    }

    public void removeCriteria(CriteriaJob criteria){
        criterias.remove(criteria);
    }

    public List<Job> meetAll(List<Job> jobs){
        List<Job> answer = new ArrayList<Job>(jobs);
        for (CriteriaJob cur : criterias){
            answer = cur.meetCriteria(answer);
        }
        return answer;
    }
}
