package cn.leo.demo.api.facade;

import cn.leo.demo.api.po.Constant;
import cn.leo.demo.api.po.Page;
import cn.leo.demo.api.po.WorkflowOperateResult;
import cn.leo.demo.api.po.WorkflowTask;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

/**
 * 流程任务facade
 */
@RequestMapping("/workflowTaskFacade")
public interface WorkflowTaskFacade {

    /**
     * 待办的任务
     */
    @RequestMapping(value = "/todoList", method = RequestMethod.GET)
    Page<WorkflowTask> todoList(String userId, String orderNo, Date sTime, Date eTime, Integer pageNo, Integer pageSize);

    /**
     * 完成任务
     */
    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    WorkflowOperateResult complete(String taskId, String userId, Constant.TaskResult result, String comment);

    /**
     * 待认领任务
     */
    @RequestMapping(value = "/waitClaimList", method = RequestMethod.GET)
    Page<WorkflowTask> waitClaimList(String userId, String orderNo, Date sTime, Date eTime, Integer pageNo, Integer pageSize);

    /**
     * 认领任务
     */
    @RequestMapping(value = "/claim", method = RequestMethod.GET)
    WorkflowOperateResult claim(String taskId, String userId);

    /**
     * 已完成的任务
     */
    @RequestMapping(value = "/finishedList", method = RequestMethod.GET)
    Page<WorkflowTask> finishedList(String userId, String orderNo, Date sTime, Date eTime, Integer pageNo, Integer pageSize);

}
