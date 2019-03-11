package cn.leo.demo.po;

public class Constant {

    public static final String _RESULT = "_result";

    public enum TaskResult {

        PASS(1,"通过"),
        REJECT(0,"驳回"),
        REJECT_2_ENTRY(-1,"驳回到进件");

        private int code;
        private String cnName;

        private TaskResult(int code, String cnName){
            this.code = code;
            this.cnName = cnName;
        }

        public static TaskResult getTaskResult(String name){
            TaskResult[] values = TaskResult.values();
            for (TaskResult value : values) {
                if(value.name().equals(name)){
                    return value;
                }
            }
            return null;
        }

    }


}
