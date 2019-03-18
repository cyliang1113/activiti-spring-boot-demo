package cn.leo.demo.service;

import cn.leo.demo.api.po.WorkflowOperateResult;
import cn.leo.demo.api.po.WorkflowProcess;

import java.util.List;

/**
 * 流程service
 */
public interface WorkflowProcessService {

    /**
     * 流程定义list
     */
    List<WorkflowProcess> definitionList();

    /**
     * 开始流程
     */
    WorkflowOperateResult start(String processKey, String userId);

    /**
     * 开始直租流程
     */
    WorkflowOperateResult startDirectLeaseProcess(String userId, String orderNo);

    /**
     * 流程取消
     */
    WorkflowOperateResult cancel(String processId, String reason);
}
