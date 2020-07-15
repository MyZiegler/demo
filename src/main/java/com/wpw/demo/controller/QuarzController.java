package com.wpw.demo.controller;

import com.wpw.demo.scheduler.QuartzSchedulerManager;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * quarzcontroller
 **/
@RestController
@RequestMapping("/quartz")
public class QuarzController {

    @Autowired
    private QuartzSchedulerManager quartzSchedulerManager;

    @RequestMapping("/start")
    public void startQuartzJob() {
        try {
            quartzSchedulerManager.startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/info")
    public String getQuartzJob() {
        String info = null;
        try {
            info = quartzSchedulerManager.getJobInfo(QuartzSchedulerManager.JOB_NAME, QuartzSchedulerManager.JOB_GROUP);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    @RequestMapping("/modify")
    public boolean modifyQuartzJob(Integer time) {
        boolean flag = true;
        try {
            flag = quartzSchedulerManager.modifyJob(time);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @RequestMapping(value = "/pause")
    public void pauseQuartzJob() {
        try {
            quartzSchedulerManager.pauseJob(QuartzSchedulerManager.JOB_NAME, QuartzSchedulerManager.JOB_GROUP);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/pauseAll")
    public void pauseAllQuartzJob() {
        try {
            quartzSchedulerManager.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/delete")
    public void deleteJob() {
        try {
            quartzSchedulerManager.deleteJob(QuartzSchedulerManager.JOB_NAME, QuartzSchedulerManager.JOB_GROUP);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
