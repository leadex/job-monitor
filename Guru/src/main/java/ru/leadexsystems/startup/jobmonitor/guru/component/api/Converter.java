package ru.leadexsystems.startup.jobmonitor.guru.component.api;

import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import net.htmlparser.jericho.Source;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexar.
 */
public class Converter {

    public static List<Job> convertToListJob(SyndFeed syndFeed){
        List<Job> answer = new ArrayList<>();
        for (Object cur : syndFeed.getEntries()){
            answer.add(convertToJob(cur));
        }
        return answer;
    }

    public static Job convertToJob(Object entry) {
        if (!(entry instanceof SyndEntry)){
            throw new IllegalArgumentException("type of 1th parameter is not SyndEntry");
        }
        SyndEntry syndEntry = (SyndEntry) entry;

        if (syndEntry.getModules().size() == 0){
            throw new IllegalArgumentException("entry don't have modules");
        }
        if (!(syndEntry.getModules().get(0) instanceof DCModule)){
            throw new IllegalArgumentException("type of 1th parameter is not SyndEntry");
        }

        DCModule dcModule = (DCModule) syndEntry.getModules().get(0);
        Job res = new Job();

        Source source=new Source(syndEntry.getDescription().getValue());
        res.setSnippet(source.getRenderer().toString());

        res.setJobSource(JobSource.guru);
        res.setCreateDate(dcModule.getDate());
        res.setTitle(syndEntry.getTitle());
        res.setUrl(syndEntry.getLink());
        res.setId(syndEntry.getLink());

        return res;
    }

}
