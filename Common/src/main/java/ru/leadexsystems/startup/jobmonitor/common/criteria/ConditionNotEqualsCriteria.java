package ru.leadexsystems.startup.jobmonitor.common.criteria;

import ru.leadexsystems.startup.jobmonitor.common.model.Condition;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Алексей on 08.11.2016.
 */
public class ConditionNotEqualsCriteria implements CriteriaJob {

    private String nameField;
    private Set<Object> unsuitableValues;

    public ConditionNotEqualsCriteria(String nameField, Set<Object> unsuitableValues) {
        this.nameField = nameField;
        this.unsuitableValues = unsuitableValues;
    }

    public ConditionNotEqualsCriteria(Condition condition) {
        this.nameField = condition.getNameField();
        this.unsuitableValues = new HashSet<>(condition.getValues());
    }

    @Override
    public List<Job> meetCriteria(List<Job> jobs) {

        List<Job> answer = new ArrayList<>();

        for (Job cur : jobs){
            Object value = cur.getValue(nameField);
            if (!unsuitableValues.contains(value)){
                answer.add(cur);
            }
        }

        return answer;
    }

}