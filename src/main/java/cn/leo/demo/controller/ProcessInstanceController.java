package cn.leo.demo.controller;

import cn.leo.demo.po.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 流程实例controller
 */
@Controller
@RequestMapping("/processInstance")
public class ProcessInstanceController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/processList")
    public List<ProcessDefinition> processList(){
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        return list;
    }

}
