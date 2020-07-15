package com.wpw.demo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * manager
 **/
@Slf4j
@Configuration
public class QuartzSchedulerManager {


    public static final String JOB_NAME = "job1";
    public static final String JOB_GROUP = "group1";
    public static final String SINGLE_JOB_NAME = "singleJob:";

    // 任务调度
    @Autowired
    private Scheduler scheduler;

    /**
     * 开始执行所有任务
     *
     * @throws SchedulerException
     */
    public void startJob() throws SchedulerException {
        startJob1(scheduler);
        scheduler.start();
    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        SimpleTrigger simpleTrigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,count%s,state:%s", simpleTrigger.getTimesTriggered(), simpleTrigger.getRepeatCount(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param time
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(Integer time) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(JOB_NAME, JOB_GROUP);

        SimpleTrigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(JOB_NAME, JOB_GROUP)
                .usingJobData("execSwitch", 1)
                .usingJobData("intervalTime", 1)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2)
                        .repeatForever()).build();
        date = scheduler.rescheduleJob(triggerKey, simpleTrigger);
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }

    private void startJob1(Scheduler scheduler) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        JobDetail jobDetail = JobBuilder.newJob(LocationJob.class).withIdentity(JOB_NAME, JOB_GROUP).build();
        SimpleTrigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .usingJobData("execSwitch", 1)
                .usingJobData("intervalTime", 1)
                .withIdentity(JOB_NAME, JOB_GROUP).startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
                        .repeatForever()).build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }

}
