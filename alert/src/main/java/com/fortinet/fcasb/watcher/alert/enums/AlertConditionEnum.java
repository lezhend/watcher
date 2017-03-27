package com.fortinet.fcasb.watcher.alert.enums;

/**
 * Created by zliu on 17/3/26.
 */
public enum AlertConditionEnum {
    LE("le"),
    GE("ge"),
    LT("lt"),
    EQ("eq"),
    GT("gt");
    private String value;

    AlertConditionEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
