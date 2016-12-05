package ru.leadexsystems.startup.jobmonitor.upwork.component.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alexar.
 */
public class Converter {

    private static final String SNIPPET = "snippet";
    private static final String CATEGORY = "category2";
    private static final String JOB_TYPE = "job_type";
    private static final String DATE_CREATE="date_created";
    private static final String TITLE="title";
    private static final String URL="url";
    private static final String SKILLS="skills";
    private static final String DURATION="duration";
    private static final String JOB_STATUS="job_status";
    private static final String CLIENT="client";
    private static final String BUDGET="budget";
    private static final String ID="id";
    
    public static List<Job> convertToListJob(JSONArray json) {
        List<Job> res = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++){
                res.add(Converter.convertToJob((JSONObject) json.get(i)));
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Job convertToJob(JSONObject json) throws JSONException, ParseException {
        Job res = new Job();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);

        res.setJobSource(JobSource.upwork);
        res.setSnippet(getString(json, SNIPPET));
        res.setCategory(getString(json,CATEGORY));
        res.setJobType(getString(json,JOB_TYPE));
        res.setCreateDate(format.parse(json.getString(DATE_CREATE)));
        res.setTitle(getString(json,TITLE));
        res.setUrl(getString(json,URL));
        res.setDuration(getString(json,DURATION));
        res.setJobStatus(getString(json,JOB_STATUS));
        res.setBudget(getString(json,BUDGET));
        res.setClientInfo(convertToClientInfo(json.getJSONObject(CLIENT)));
        res.setId(getString(json,ID));
        res.setRegion(getString(json.getJSONObject(CLIENT), "country"));

        JSONArray skills = json.getJSONArray(SKILLS);
        List<String> listSkill = new ArrayList<>();
        if (skills != null){
            for(int i = 0; i < skills.length(); i++){
                listSkill.add(skills.get(i).toString());
            }
        }
        res.setSkills(listSkill);

        return res;
    }

    public static String convertToClientInfo(JSONObject json) throws JSONException {
        return getString(json,"country");
    }

    private static String getString(JSONObject json, String key) throws JSONException {
        Object obj = json.get(key);
        if (obj != null && !"null".equals(obj.toString())){
            return obj.toString();
        }
        return null;
    }

}
