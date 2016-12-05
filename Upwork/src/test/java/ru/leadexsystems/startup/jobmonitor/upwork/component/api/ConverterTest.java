package ru.leadexsystems.startup.jobmonitor.upwork.component.api;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by alexar.
 */
public class ConverterTest {


    @Before
    public void setUp() throws Exception {

    }

    String jsonJob = "{\"jobs\":[{ \"snippet\":null,\n" +
            "  \"category2\":\"Admin Support\",\n" +
            "  \"job_type\":\"Fixed\",\n" +
            "  \"date_created\":\"2016-10-26T07:00:16+0000\",\n" +
            "  \"title\":\"I need full time Virtual Assistants - Long Term Job\",\n" +
            "  \"url\":\"http://www.upwork.com/jobs/~019e5cd72bbb284d50\",\n" +
            "  \"skills\":[\"english\",\"internet-research\",\"social-media-management\",\"virtual-assistant\",\"writing\"],\n" +
            "  \"duration\":null,\n" +
            "  \"subcategory2\":\"Personal / Virtual Assistant\",\n" +
            "  \"job_status\":\"Open\",\n" +
            "  \"client\":{\"feedback\":4.9560277954,\"country\":\"Turkey\",\"past_hires\":353,\"payment_verification_status\":\"VERIFIED\",\"jobs_posted\":172,\"reviews_count\":174},\n" +
            "  \"id\":\"~019e5cd72bbb284d50\",\n" +
            "  \"budget\":300}]}";


    @Test
    public void convertToJob() throws Exception {
        JSONTokener tokener = new JSONTokener(jsonJob);
        JSONObject root = new JSONObject(tokener);
        Job res = Converter.convertToJob((JSONObject) root.getJSONArray("jobs").get(0));

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss Z");
        Date date = dateFormat.parse("26-10-2016 7:00:16 +0000");

        String[] skills = {"english","internet-research","social-media-management","virtual-assistant","writing"};

        Assert.assertEquals("300", res.getBudget());
        Assert.assertEquals("~019e5cd72bbb284d50", res.getId());
        Assert.assertEquals("Turkey", res.getClientInfo());
        Assert.assertEquals("Admin Support", res.getCategory());
        Assert.assertEquals(date, res.getCreateDate());
        Assert.assertNull(res.getDuration());
        Assert.assertEquals(JobSource.upwork, res.getJobSource());
        Assert.assertEquals("Fixed", res.getJobType());
        Assert.assertNull(res.getSnippet());
        Assert.assertEquals("I need full time Virtual Assistants - Long Term Job", res.getTitle());
        Assert.assertEquals("http://www.upwork.com/jobs/~019e5cd72bbb284d50", res.getUrl());
        Assert.assertArrayEquals(skills, res.getSkills().toArray());
    }

    @Test
    public void convertToListJob() throws Exception {
        JSONTokener tokener = new JSONTokener(jsonJob);
        JSONObject root = new JSONObject(tokener);
        List<Job> jobs = Converter.convertToListJob(root.getJSONArray("jobs"));
        Assert.assertEquals(1, jobs.size());

    }

}