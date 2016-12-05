package ru.leadexsystems.startup.jobmonitor.common.criteria;

import org.junit.Assert;
import org.junit.Test;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alexar.
 */
public class DuplicateCriteriaTest {

    @Test
    public void meetCriteria() throws Exception {
        Job job1 = mock(Job.class);
        when(job1.getUrl()).thenReturn("asd1");

        Job job2 = mock(Job.class);
        when(job2.getUrl()).thenReturn("asd2");

        Job job3 = mock(Job.class);
        when(job3.getUrl()).thenReturn("asd2");

        List<Job> jobs = new ArrayList<>();
        jobs.add(job2);
        jobs.add(job1);
        jobs.add(job3);
        jobs.add(job1);
        jobs.add(job2);

        CriteriaJob criteriaJob = new DuplicateCriteria();

        List<Job> filtered = criteriaJob.meetCriteria(jobs);
        Assert.assertEquals(2, filtered.size());
    }

    @Test
    public void meetCriteriaEmptyList() throws Exception {

        CriteriaJob criteriaJob = new DuplicateCriteria();
        List<Job> filtered = criteriaJob.meetCriteria(new ArrayList<>());
        Assert.assertEquals(0, filtered.size());
    }

}