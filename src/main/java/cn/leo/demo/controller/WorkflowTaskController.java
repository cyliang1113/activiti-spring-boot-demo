package cn.leo.demo.controller;

import cn.leo.demo.po.WorkflowTask;
import cn.leo.demo.service.WorkflowTaskService;
import cn.leo.demo.service.WorkflowTaskServiceImpl;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/workflowTask")
public class WorkflowTaskController {

    @Autowired
    private WorkflowTaskService workflowTaskService;

    /**
     * 待办任务
     *
     * @return
     */
    @RequestMapping("/todoList")
    @ResponseBody
    public List<WorkflowTask> todoList(
            @RequestParam("userId") String userId,
            @RequestParam(value = "orderNo", required = false) String orderNo,
            @RequestParam(value = "sTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date sTime,
            @RequestParam(value = "eTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date eTime,
            @RequestParam(value = "startRow", required = false) Integer startRow,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        List<WorkflowTask> workflowTasks = workflowTaskService.todoList(userId, orderNo, sTime, eTime, startRow, pageSize);
        return workflowTasks;
    }


    /**
     * 待认领任务
     * @param userId
     * @return
     */
    @RequestMapping("/waitClaimList")
    @ResponseBody
    public List<WorkflowTask> waitClaimList(
            @RequestParam("userId") String userId,
            @RequestParam(value = "orderNo", required = false) String orderNo,
            @RequestParam(value = "sTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date sTime,
            @RequestParam(value = "eTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date eTime,
            @RequestParam(value = "startRow", required = false) Integer startRow,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        List<WorkflowTask> workflowTasks = workflowTaskService.waitClaimList(userId, orderNo, sTime, eTime, startRow, pageSize);
        return workflowTasks;
    }



}
