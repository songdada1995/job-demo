package com.example.jobdemo.controller;

import com.example.jobdemo.domain.AjaxResult;
import com.example.jobdemo.entity.SysJob;
import com.example.jobdemo.domain.TableDataInfo;
import com.example.jobdemo.exception.TaskException;
import com.example.jobdemo.service.ISysJobService;
import com.example.jobdemo.util.CronUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 调度任务信息操作处理
 */
@RestController
@RequestMapping("/job")
public class SysJobController extends BaseController {
    @Autowired
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TableDataInfo list(SysJob sysJob) {
        startPage();
        List<SysJob> list = jobService.selectJobList(sysJob);
        return getDataTable(list);
    }

    /**
     * 导出定时任务列表
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletResponse response, SysJob sysJob) throws IOException {

    }

    /**
     * 获取定时任务详细信息
     */
    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId) {
        return AjaxResult.success(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult add(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            return AjaxResult.error("cron表达式不正确");
        }
        sysJob.setCreateBy("system");
        return toAjax(jobService.insertJob(sysJob));
    }

    /**
     * 修改定时任务
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public AjaxResult edit(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            return AjaxResult.error("cron表达式不正确");
        }
        sysJob.setUpdateBy("system");
        return toAjax(jobService.updateJob(sysJob));
    }

    /**
     * 定时任务状态修改
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.PUT)
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return toAjax(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @RequestMapping(value = "/run", method = RequestMethod.PUT)
    public AjaxResult run(@RequestBody SysJob job) throws SchedulerException {
        jobService.run(job);
        return AjaxResult.success();
    }

    /**
     * 删除定时任务
     */
    @RequestMapping(value = "/{jobIds}", method = RequestMethod.DELETE)
    public AjaxResult remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException {
        jobService.deleteJobByIds(jobIds);
        return AjaxResult.success();
    }
}
