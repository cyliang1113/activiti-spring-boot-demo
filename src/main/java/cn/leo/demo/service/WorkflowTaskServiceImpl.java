package cn.leo.demo.service;

import cn.leo.demo.po.Constant;
import cn.leo.demo.po.WorkflowOperateResult;
import cn.leo.demo.po.WorkflowTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static cn.leo.demo.po.Constant._RESULT;

@Service("workflowTaskService")
public class WorkflowTaskServiceImpl implements WorkflowTaskService {
    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;


    @Override
    public List<WorkflowTask> todoList(String userId, String orderNo, Date sTime, Date eTime, Integer startRow, Integer pageSize) {
        if(startRow == null || pageSize == null){
            startRow = 0;
            pageSize = 10;
        }
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotBlank(userId)){
            taskQuery.taskAssignee(userId);
        }
        if (StringUtils.isNotBlank(orderNo)){
            taskQuery.processInstanceBusinessKey(orderNo);
        }
        if(sTime != null){
            taskQuery.taskCreatedAfter(sTime);
        }
        if(eTime != null){
            taskQuery.taskCreatedAfter(eTime);
        }
        List<Task> tasks = taskQuery.listPage(startRow, pageSize);
        return transformTask(tasks);
    }

    @Override
    public WorkflowOperateResult complete(String taskId, String userId, Constant.TaskResult result, String comment) {
        if(StringUtils.isBlank(taskId)){
            return WorkflowOperateResult.operateFailure("任务id为空.");
        }
        if(StringUtils.isBlank(userId)){
            return WorkflowOperateResult.operateFailure("用户id为空.");
        }
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(taskId).taskAssignee(userId).singleResult();
        if (task != null) {
            if(StringUtils.isNotBlank(comment)){
                String processInstanceId = task.getProcessInstanceId();
                taskService.addComment(taskId, processInstanceId, comment);
            }
            Map<String, Object> variables = new HashMap<String, Object>();
            String taskDefinitionKey = task.getTaskDefinitionKey();
            variables.put(taskDefinitionKey + _RESULT, result.name());
            taskService.complete(taskId, variables);
            return WorkflowOperateResult.operateSuccess();
        } else {
            return WorkflowOperateResult.operateFailure("任务不存在.");
        }
    }

    @Override
    public List<WorkflowTask> waitClaimList(String userId, String orderNo, Date sTime, Date eTime, Integer startRow, Integer pageSize) {
        if(startRow == null || pageSize == null){
            startRow = 0;
            pageSize = 10;
        }
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotBlank(userId)){
            taskQuery.taskCandidateUser(userId);
        }
        if (StringUtils.isNotBlank(orderNo)){
            taskQuery.processInstanceBusinessKey(orderNo);
        }
        if(sTime != null){
            taskQuery.taskCreatedAfter(sTime);
        }
        if(eTime != null){
            taskQuery.taskCreatedAfter(eTime);
        }
        List<Task> tasks = taskQuery.listPage(startRow, pageSize);
        return transformTask(tasks);
    }

    @Override
    public WorkflowOperateResult claim(String taskId, String userId) {
        if(StringUtils.isBlank(taskId)){
            return WorkflowOperateResult.operateFailure("任务id为空.");
        }
        if(StringUtils.isBlank(userId)){
            return WorkflowOperateResult.operateFailure("用户id为空.");
        }
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(taskId).taskCandidateUser(userId).singleResult();
        if (task != null) {
            taskService.claim(taskId, userId);
            return WorkflowOperateResult.operateSuccess();
        } else {
            return WorkflowOperateResult.operateFailure("任务不存在.");
        }
    }

    @Override
    public List<WorkflowTask> finishedList(String userId, String orderNo, Date sTime, Date eTime, Integer startRow, Integer pageSize) {
        if(startRow == null || pageSize == null){
            startRow = 0;
            pageSize = 10;
        }
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        if (StringUtils.isNotBlank(userId)){
            historicTaskInstanceQuery.taskAssignee(userId);
        }
        if (StringUtils.isNotBlank(orderNo)){
            historicTaskInstanceQuery.processInstanceBusinessKey(orderNo);
        }
        if(sTime != null){
            historicTaskInstanceQuery.taskCreatedAfter(sTime);
        }
        if(eTime != null){
            historicTaskInstanceQuery.taskCreatedAfter(eTime);
        }
        List<HistoricTaskInstance> tasks = historicTaskInstanceQuery.listPage(startRow, pageSize);
        return transformHistoricTask(tasks);
    }

    /**
     *
     */
    private List<WorkflowTask> transformTask(List<Task> tasks){
        List<WorkflowTask> todoList = new LinkedList<>();
        if (tasks != null && !tasks.isEmpty()){
            Set<String> procIdSet = new HashSet<>();
            for (Task task : tasks) {
                procIdSet.add(task.getProcessInstanceId());
                WorkflowTask taskInst = new WorkflowTask();
                taskInst.setId(task.getId());
                taskInst.setProcessInstanceId(task.getProcessInstanceId());
                taskInst.setName(task.getName());
                taskInst.setCreateTime(task.getCreateTime());
                taskInst.setAssignee(task.getAssignee());
                todoList.add(taskInst);
            }
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(procIdSet).list();
            Map<String, ProcessInstance> processMap = new HashMap<>();
            for (ProcessInstance processInstance : processInstanceList) {
                processMap.put(processInstance.getId(), processInstance);
            }
            for (WorkflowTask taskInst : todoList) {
                String processInstanceId = taskInst.getProcessInstanceId();
                ProcessInstance processInstance = processMap.get(processInstanceId);
                if(processInstance != null){
                    taskInst.setProcessName(processInstance.getName());
                    taskInst.setOrderNo(processInstance.getBusinessKey());
                }
            }
        }
        return todoList;
    }

    private List<WorkflowTask> transformHistoricTask(List<HistoricTaskInstance> tasks){
        List<WorkflowTask> todoList = new LinkedList<>();
        if (tasks != null && !tasks.isEmpty()){
            Set<String> procIdSet = new HashSet<>();
            for (HistoricTaskInstance task : tasks) {
                procIdSet.add(task.getProcessInstanceId());
                WorkflowTask taskInst = new WorkflowTask();
                taskInst.setId(task.getId());
                taskInst.setProcessInstanceId(task.getProcessInstanceId());
                taskInst.setName(task.getName());
                taskInst.setCreateTime(task.getCreateTime());
                taskInst.setAssignee(task.getAssignee());
                todoList.add(taskInst);
            }
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(procIdSet).list();
            Map<String, ProcessInstance> processMap = new HashMap<>();
            for (ProcessInstance processInstance : processInstanceList) {
                processMap.put(processInstance.getId(), processInstance);
            }
            for (WorkflowTask taskInst : todoList) {
                String processInstanceId = taskInst.getProcessInstanceId();
                ProcessInstance processInstance = processMap.get(processInstanceId);
                if(processInstance != null){
                    taskInst.setProcessName(processInstance.getName());
                    taskInst.setOrderNo(processInstance.getBusinessKey());
                }
            }
        }
        return todoList;
    }
}
