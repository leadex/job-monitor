package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexar.
 */
public class DateCriteria implements CriteriaJob {

    private Date dateLastQuery;

    public DateCriteria() {
        dateLastQuery = new Date();
    }

    public DateCriteria(Date dateLastQuery) {
        if (this.dateLastQuery == null) {
            this.dateLastQuery = dateLastQuery;
        }
    }

    @Override
    public List<Job> meetCriteria(List<Job> jobs) {
        List<Job> res = new ArrayList<>();
        for (Job cur : jobs){
            if (cur.getCreateDate().after(dateLastQuery)){
                res.add(cur);
            }
        }
        return res;
    }

}
