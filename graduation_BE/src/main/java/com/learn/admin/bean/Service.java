package com.learn.admin.bean;

import lombok.Data;

import java.util.List;

@Data
public class Service {
    private String name;
    private String type;
    private String namespace;
    private String matchLabelsApp;
    private String clusterIp;
    private String port;
//    private String nodePort;


    public Service(String name, String type, String namespace,String matchLabelsApp,String clusterIp, String port) {
        this.name = name;
        this.type = type;
        this.namespace = namespace;
        this.matchLabelsApp = matchLabelsApp;
        this.clusterIp = clusterIp;
        this.port = port;
//        this.nodePort = nodePort;
    }
}
