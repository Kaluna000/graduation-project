package com.learn.admin.bean;

import lombok.Data;

@Data
public class NodePortService {
    private String name;
    private String type;
    private String namespace;
    private String matchLabelsApp;
    private String clusterIp;
    private String port;
    private Integer nodePort;

    public NodePortService(String name, String type, String namespace, String matchLabelsApp, String clusterIp, String port, Integer nodePort) {
        this.name = name;
        this.type = type;
        this.namespace = namespace;
        this.matchLabelsApp = matchLabelsApp;
        this.clusterIp = clusterIp;
        this.port = port;
        this.nodePort = nodePort;
    }
}
