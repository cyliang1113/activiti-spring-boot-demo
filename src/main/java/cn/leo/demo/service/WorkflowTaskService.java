package cn.leo.demo.service;

import cn.leo.demo.po.Constant;
import cn.leo.demo.po.WorkflowOperateResult;
import cn.leo.demo.po.WorkflowTask;

import java.util.Date;
import java.util.List;

/**
 * 流程任务service
 */
public interface WorkflowTaskService {

    /**
     * 待办的任务
     */
    List<WorkflowTask> todoList(String userId, String orderNo, Date sTime, Date eTime, Integer pageNo, Integer pageSize);

    /**
     * 完成任务
     */
    WorkflowOperateResult complete(String taskId, String userId, Constant.TaskResult result, String comment);

    /**
     * 待认领任务
     */
    List<WorkflowTask> waitClaimList(String userId, String orderNo, Date sTime, Date eTime, Integer startRow, Integer pageSize);

    /**
     * 认领任务
     */
    WorkflowOperateResult claim(String taskId, String userId);

    /**
     * 已完成的任务
     */
    List<WorkflowTask> finishedList(String userId, String orderNo, Date sTime, Date eTime, Integer startRow, Integer pageSize);

}
