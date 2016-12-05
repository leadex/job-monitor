package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Алексей on 08.11.2016.
 */
public class DescriptionUniqCriteria implements CriteriaJob {
    @Override
    public List<Job> meetCriteria(List<Job> list) {
        Set<String> setDesc = new HashSet<>();
        List<Job> answer = new ArrayList<>();

        for (Job cur : list){
            if (!setDesc.contains(cur.getSnippet())){
                answer.add(cur);
                setDesc.add(cur.getSnippet());
            }
        }

        return answer;
    }
}
