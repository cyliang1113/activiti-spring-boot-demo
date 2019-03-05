package cn.leo.demo.controller;

import cn.leo.demo.po.ProcessDef;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/definitionList")
    @ResponseBody
    public List<ProcessDef> definitionList(){
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.latestVersion().list();
        ArrayList<ProcessDef> processDefs = new ArrayList<>();
        for (ProcessDefinition processDefinition : list) {
            ProcessDef processDef = new ProcessDef();
            processDef.setKey(processDefinition.getKey());
            processDef.setVersion(processDefinition.getVersion());
            processDef.setName(processDefinition.getName());
            processDefs.add(processDef);
        }
        return processDefs;
    }

}
