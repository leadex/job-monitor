package ru.leadexsystems.startup.jobmonitor.common.criteria;

import org.junit.Assert;
import org.junit.Test;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alexar.
 */
public class DateCriteriaTest {


    @Test
    public void meetCriteria() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z");
        Date dateBefore, date, dateAfter;

        dateBefore = dateFormat.parse("19-10-2016 7:00:16 +0000");
        date = dateFormat.parse("20-10-2016 7:00:16 +0000");
        dateAfter = dateFormat.parse("21-10-2016 7:00:16 +0000");

        Job mockJobBefore = mock(Job.class);
        when(mockJobBefore.getCreateDate()).thenReturn(dateBefore);

        Job mockJobAfter = mock(Job.class);
        when(mockJobAfter.getCreateDate()).thenReturn(dateAfter);

        List<Job> jobs = new ArrayList<>();
        jobs.add(mockJobAfter);
        jobs.add(mockJobBefore);

        CriteriaJob criteriaJob = new DateCriteria(date);
        List<Job> filtered = criteriaJob.meetCriteria(jobs);

        Assert.assertEquals(1, filtered.size());
    }

    @Test
    public void meetCriteriaEmptyList() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z");
        Date date = dateFormat.parse("20-10-2016 7:00:16 +0000");

        CriteriaJob criteriaJob = new DateCriteria(date);
        List<Job> filtered = criteriaJob.meetCriteria(new ArrayList<>());

        Assert.assertEquals(0, filtered.size());
    }

}