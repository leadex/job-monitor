package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Condition;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.*;

/**
 * Created by Алексей on 08.11.2016.
 */
public class ConditionEqualsCriteria implements CriteriaJob {

    private String nameField;
    private Set<Object> suitableValues;

    public ConditionEqualsCriteria(String nameField, Set<Object> suitableValues) {
        this.nameField = nameField;
        this.suitableValues = suitableValues;
    }

    public ConditionEqualsCriteria(Condition condition) {
        this.nameField = condition.getNameField();
        this.suitableValues = new HashSet<>(condition.getValues());
    }


    @Override
    public List<Job> meetCriteria(List<Job> jobs) {

        List<Job> answer = new ArrayList<>();

        for (Job cur : jobs){
            Object value = cur.getValue(nameField);
            if (suitableValues.contains(value)){
                answer.add(cur);
            }
        }

        return answer;
    }

}
