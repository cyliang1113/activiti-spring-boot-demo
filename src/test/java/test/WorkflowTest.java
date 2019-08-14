package test;

import cn.leo.demo.ActivitiSpringBootDemo;
import cn.leo.demo.api.po.Constant;
import cn.leo.demo.api.po.WorkflowOperateResult;
import cn.leo.demo.api.po.WorkflowTask;
import cn.leo.demo.service.WorkflowProcessService;
import cn.leo.demo.service.WorkflowTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ActivitiSpringBootDemo.class})
public class WorkflowTest {

    @Autowired
    private WorkflowProcessService workflowProcessService;

    @Autowired
    private WorkflowTaskService workflowTaskService;

    @Test
    public void startProcess() {
        workflowProcessService.start("directLeaseProcess", "test");
    }

    @Test
    public void queryTask() {
        List<WorkflowTask> workflowTasks = workflowTaskService.todoList(null, null, null, null, null, null);
        log.info(workflowTasks.toString());
    }

    @Test
    public void completeTask() {
        String taskId = "2505";
        WorkflowOperateResult operateResult = workflowTaskService.complete(taskId, "100", Constant.TaskResult.PASS, "");
        log.info(operateResult.isSuccess() + ", " + operateResult.getMessage());
    }





}
