package cn.leo.demo.service;

import cn.leo.demo.api.po.WorkflowOperateResult;
import cn.leo.demo.api.po.WorkflowProcess;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class WorkflowProcessServiceImpl implements WorkflowProcessService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 流程定义list
     */
    @Override
    public List<WorkflowProcess> definitionList() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.latestVersion().list();
        ArrayList<WorkflowProcess> workflowProcessList = new ArrayList<>();
        for (ProcessDefinition processDefinition : list) {
            WorkflowProcess processDef = new WorkflowProcess();
            processDef.setKey(processDefinition.getKey());
            processDef.setVersion(processDefinition.getVersion());
            processDef.setName(processDefinition.getName());
            workflowProcessList.add(processDef);
        }
        return workflowProcessList;
    }

    /**
     * 流程取消
     */
    @Override
    public WorkflowOperateResult cancel(String processId, String reason) {
        runtimeService.deleteProcessInstance(processId, reason);
        return WorkflowOperateResult.operateSuccess();
    }
}
