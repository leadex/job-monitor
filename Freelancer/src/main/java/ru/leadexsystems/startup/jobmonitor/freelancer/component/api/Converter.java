package ru.leadexsystems.startup.jobmonitor.freelancer.component.api;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.freelancer.component.api.model.FreelancerProject;
import ru.leadexsystems.startup.jobmonitor.freelancer.component.api.model.FreelancerResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alexar.
 */
public class Converter {

    public static List<Job> convertToListJob(FreelancerResult result){
        List<Job> answer = new ArrayList<>();
        for (FreelancerProject cur : result.getProjects()){
            answer.add(convertToJob(cur));
        }
        return answer;
    }

    public static Job convertToJob(FreelancerProject project) {
        Job res = new Job();

        res.setJobSource(JobSource.freelancer);
        res.setSnippet(project.getPreviewDescription());
        res.setJobType(project.getType());

        long sec = project.getSubmitdate();
        res.setCreateDate(new Date(sec * 1000L));
        res.setTitle(project.getTitle());
        res.setUrl("https://www.freelancer.com/projects/" + project.getSeoUrl());
        res.setJobStatus(project.getStatus());
        res.setBudget(getBudget(project.getBudget(), project.getCurrency()));
        res.setId(project.getId().toString());
        res.setRegion(project.getLocation().getCountry().getName());

        List<String> listSkill = new ArrayList<>();
        res.setSkills(listSkill);

        return res;
    }

    private static String getBudget(FreelancerProject.Budget budget, FreelancerProject.Currency currency) {
        return String.format("from %s to %s (%s)", budget.getMinimum(), budget.getMaximum(), currency.getCode());
    }

}
