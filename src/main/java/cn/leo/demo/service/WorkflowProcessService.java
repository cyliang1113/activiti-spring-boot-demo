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
     * 流程取消
     */
    WorkflowOperateResult cancel(String processId, String reason);

}
