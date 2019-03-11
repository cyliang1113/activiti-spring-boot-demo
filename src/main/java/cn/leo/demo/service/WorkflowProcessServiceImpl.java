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

    @Override
    public List<WorkflowProcess> definitionList() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.latestVersion().list();
        ArrayList<WorkflowProcess> processDefs = new ArrayList<>();
        for (ProcessDefinition processDefinition : list) {
            WorkflowProcess processDef = new WorkflowProcess();
            processDef.setKey(processDefinition.getKey());
            processDef.setVersion(processDefinition.getVersion());
            processDef.setName(processDefinition.getName());
            processDefs.add(processDef);
        }
        return processDefs;
    }

    @Override
    public WorkflowOperateResult cancel(String processId, String reason) {
        runtimeService.deleteProcessInstance(processId, reason);
        return WorkflowOperateResult.operateSuccess();
    }
}
