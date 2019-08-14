package cn.leo.demo.service;

import cn.leo.demo.api.po.Constant;
import cn.leo.demo.api.po.WorkflowOperateResult;
import cn.leo.demo.api.po.WorkflowProcess;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowProcessServiceImpl implements WorkflowProcessService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

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
     * 开始流程
     */
    @Override
    @Transactional
    public WorkflowOperateResult start(String processKey, String userId) {
        if(StringUtils.isBlank(processKey)){
            return WorkflowOperateResult.operateFailure("processKey为空.");
        }
        if(StringUtils.isBlank(userId)){
            return WorkflowOperateResult.operateFailure("userId为空.");
        }
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition pD = processDefinitionQuery.processDefinitionKey(processKey).latestVersion().singleResult();
        if(pD == null || pD.getId() == null){
            return WorkflowOperateResult.operateFailure("流程(" + processKey +")不存在");
        }
        identityService.setAuthenticatedUserId(userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(pD.getId());
        int i = 1 / 0;
        return WorkflowOperateResult.operateSuccess();
    }

    /**
     * 开始直租流程
     */
    @Override
    public WorkflowOperateResult startDirectLeaseProcess(String userId, String orderNo) {
        if(StringUtils.isBlank(userId)){
            return WorkflowOperateResult.operateFailure("userId为空.");
        }
        if(StringUtils.isBlank(orderNo)){
            return WorkflowOperateResult.operateFailure("orderNo为空.");
        }
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition pD = processDefinitionQuery.processDefinitionKey(Constant.DIRECT_LEASE_PROCESS_KEY).latestVersion().singleResult();
        if(pD == null || pD.getId() == null){
            return WorkflowOperateResult.operateFailure("流程不存在.");
        }
        identityService.setAuthenticatedUserId(userId);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("entryUser", userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(pD.getId(), orderNo, variables);
        return WorkflowOperateResult.operateSuccess();
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
