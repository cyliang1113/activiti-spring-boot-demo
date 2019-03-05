package cn.leo.demo.controller;

import cn.leo.demo.po.ProcessDef;
import cn.leo.demo.po.TaskInst;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping("/task")
public class TaskController {
    public static final String _RESULT = "_result";



    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    /**
     * 待办任务
     *
     * @return
     */
    @RequestMapping("/todoList")
    @ResponseBody
    public List<TaskInst> todoList(String userId, String orderNO, Date sTime, Date eTime) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(userId);
        if(StringUtils.isNotBlank(orderNO)){
            taskQuery.processInstanceBusinessKey(orderNO);
        }
        if(sTime != null){
            taskQuery.taskCreatedAfter(sTime);
        }
        if(eTime != null){
            taskQuery.taskCreatedBefore(eTime);
        }
        List<Task> list = taskQuery.list();
        LinkedList<TaskInst> taskList = new LinkedList<>();
        for (Task task : list) {
            taskList.add(fillTaskInst(task));
        }
        return taskList;
    }

    /**
     * 完成任务
     * @param taskId
     * @param userId
     * @param result
     * @param comment
     * @return
     */
    @RequestMapping("/complete")
    @ResponseBody
    public String complete(String taskId, String userId, String result, String comment) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(taskId).taskAssignee(userId).singleResult();
        if (task != null) {
            if(StringUtils.isNotBlank(comment)){
                String processInstanceId = task.getProcessInstanceId();
                taskService.addComment(taskId, processInstanceId, comment);
            }
            Map<String, Object> variables = new HashMap<String, Object>();
            if(StringUtils.isNotBlank(result)){
                String taskDefinitionKey = task.getTaskDefinitionKey();
                variables.put(taskDefinitionKey + _RESULT, result);
            }
            taskService.complete(taskId, variables);
            return "完成.";
        } else {
            return "任务不存在.";
        }

    }

    /**
     * 待认领任务
     * @param userId
     * @return
     */
    @RequestMapping("/waitClaimList")
    @ResponseBody
    public List<TaskInst> waitClaimList(String userId){
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskCandidateUser(userId).list();
        LinkedList<TaskInst> taskList = new LinkedList<>();
        for (Task task : list) {
            taskList.add(fillTaskInst(task));
        }
        return taskList;
    }

    /**
     * 认领任务
     * @param taskId
     * @param userId
     * @return
     */
    @RequestMapping("/claim")
    @ResponseBody
    public String claim(String taskId, String userId){
//        identityService;
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(taskId).taskCandidateUser(userId).singleResult();
        if (task != null) {
            taskService.claim(taskId, userId);
            return "认领成功.";
        }else{
            return "任务不存在.";
        }
    }

    @RequestMapping("/done")
    @ResponseBody
    public List<TaskInst> done(String userId, String orderNo){
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.taskAssignee(userId);
        historicTaskInstanceQuery.taskCompletedBefore(new Date());
        if (StringUtils.isNotBlank(orderNo)) {
            historicTaskInstanceQuery.processInstanceBusinessKey(orderNo);
        }
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.list();
        LinkedList<TaskInst> taskList = new LinkedList<>();
        for (HistoricTaskInstance historicTaskInstance : list) {
            TaskInst t = new TaskInst();
            t.setId(historicTaskInstance.getId());
            t.setName(historicTaskInstance.getName());
            t.setAssignee(historicTaskInstance.getAssignee());
            t.setCreateTime(historicTaskInstance.getCreateTime());
            t.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
            t.setProcessName(processInstance.getName());
            t.setBusinessKey(processInstance.getBusinessKey());
            taskList.add(t);
        }
        return taskList;
    }

    private List<TaskInst> convert(List<Task> list) {
        LinkedList<TaskInst> taskList = new LinkedList<>();
        for (Task task : list)
            taskList.add(fillTaskInst(task));
        return taskList;
    }

    private TaskInst fillTaskInst(Task task){
        TaskInst t = new TaskInst();
        t.setId(task.getId());
        t.setName(task.getName());
        t.setAssignee(task.getAssignee());
        t.setCreateTime(task.getCreateTime());
        t.setProcessInstanceId(task.getProcessInstanceId());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        t.setProcessName(processInstance.getName());
        t.setBusinessKey(processInstance.getBusinessKey());
        return t;
    }

}
