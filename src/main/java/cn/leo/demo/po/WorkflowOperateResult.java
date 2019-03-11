package cn.leo.demo.po;

public class WorkflowOperateResult {
    private boolean success;
    private String message;

    private WorkflowOperateResult(){

    }

    public static WorkflowOperateResult operateSuccess(){
        WorkflowOperateResult result = new WorkflowOperateResult();
        result.success = true;
        return result;
    }

    public static WorkflowOperateResult operateFailure(String message){
        WorkflowOperateResult result = new WorkflowOperateResult();
        result.success = false;
        result.message = message;
        return result;
    }

}
