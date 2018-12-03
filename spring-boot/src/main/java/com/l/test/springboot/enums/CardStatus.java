package com.l.test.springboot.enums;

/**
 * 卡片状态
 */
public enum  CardStatus {

    ON("已启用"),
    OFF("已禁用");

    private String desc;

    CardStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
