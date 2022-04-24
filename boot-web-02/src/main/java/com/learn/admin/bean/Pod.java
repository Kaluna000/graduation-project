package com.learn.admin.bean;

import lombok.Data;

import java.time.OffsetDateTime;


public class Pod {
    private String name;
    private String namespace;
    private String status;
    private String ip;
    private String startTime;

    public Pod(String name, String namespace, String status, String ip,String startTime) {
        this.name = name;
        this.namespace = namespace;
        this.status = status;
        this.ip = ip;
        this.startTime = startTime;
    }
}
