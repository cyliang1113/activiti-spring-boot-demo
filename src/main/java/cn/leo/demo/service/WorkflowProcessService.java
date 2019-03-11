package cn.leo.demo.service;

import cn.leo.demo.po.WorkflowProcess;

import java.util.List;

/**
 * 流程service
 */
public interface WorkflowProcessService {

    /**
     * 流程定义list
     */
    List<WorkflowProcess> definitionList();

}
