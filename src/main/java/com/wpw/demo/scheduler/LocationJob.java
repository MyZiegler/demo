package com.wpw.demo.scheduler;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * location
 **/
@Slf4j
public class LocationJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap triggerDataMap = jobExecutionContext.getTrigger().getJobDataMap();
            log.info("执行定位拉取任务开始");
            log.info("=====================================================");
            log.info("执行定位拉取任务结束");

    }
}
