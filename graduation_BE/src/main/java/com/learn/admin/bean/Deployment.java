package com.learn.admin.bean;

import lombok.Data;

@Data
public class Deployment {
    private String name;
    private String namespace;
    private Integer replicas;
    private Integer readyReplicas;
    private String matchLabelsApp;

    public Deployment(String name, String namespace, Integer replicas, Integer readyReplicas,String matchLabelsApp) {
        this.name = name;
        this.namespace = namespace;
        this.replicas = replicas;
        this.readyReplicas = readyReplicas;
        this.matchLabelsApp = matchLabelsApp;
    }
}
