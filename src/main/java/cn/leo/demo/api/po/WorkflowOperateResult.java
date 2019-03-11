package cn.leo.demo.api.po;

import java.io.Serializable;

public class WorkflowOperateResult implements Serializable {
    private static final long serialVersionUID = 8790982583158579549L;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
