package com.example.jobdemo.controller;

import com.example.jobdemo.domain.AjaxResult;
import com.example.jobdemo.entity.SysJobLog;
import com.example.jobdemo.domain.TableDataInfo;
import com.example.jobdemo.service.ISysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 调度日志操作处理
 */
@RestController
@RequestMapping("/job/log")
public class SysJobLogController extends BaseController {
    @Autowired
    private ISysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TableDataInfo list(SysJobLog sysJobLog) {
        startPage();
        List<SysJobLog> list = jobLogService.selectJobLogList(sysJobLog);
        return getDataTable(list);
    }

    /**
     * 导出定时任务调度日志列表
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletResponse response, SysJobLog sysJobLog) throws IOException {

    }

    /**
     * 根据调度编号获取详细信息
     */
    @RequestMapping(value = "/{configId}", method = RequestMethod.GET)
    public AjaxResult getInfo(@PathVariable Long jobLogId) {
        return AjaxResult.success(jobLogService.selectJobLogById(jobLogId));
    }

    /**
     * 删除定时任务调度日志
     */
    @RequestMapping(value = "/{jobLogIds}", method = RequestMethod.DELETE)
    public AjaxResult remove(@PathVariable Long[] jobLogIds) {
        return toAjax(jobLogService.deleteJobLogByIds(jobLogIds));
    }

    /**
     * 清空定时任务调度日志
     */
    @RequestMapping(value = "/clean", method = RequestMethod.DELETE)
    public AjaxResult clean() {
        jobLogService.cleanJobLog();
        return AjaxResult.success();
    }
}
