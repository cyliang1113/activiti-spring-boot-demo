package cn.leo.demo.api.po;


import java.io.Serializable;

/**
 * 流程定义
 */
public class WorkflowProcess implements Serializable {

    private static final long serialVersionUID = 3219067643041629496L;

    private String key;
    private int version;
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
