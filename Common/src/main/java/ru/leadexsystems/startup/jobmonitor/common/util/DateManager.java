package ru.leadexsystems.startup.jobmonitor.common.util;

import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.model.Condition;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;

import java.util.Date;
import java.util.List;

/**
 * Created by Алексей on 01.11.2016.
 */
public interface DateManager {
    Date getLastDate(String query, JobSource source) throws NotFoundParameterException;
    void setLastDate(Date date, String query, JobSource source) throws NotFoundParameterException;
}
