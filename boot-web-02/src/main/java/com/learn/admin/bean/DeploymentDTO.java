package com.learn.admin.bean;

import lombok.Data;

@Data
public class DeploymentDTO {
    private String namespace;
    private String deploymentName;
    private Integer replicas;
    private String metadataLabelsApp;
    private String image;
    private String portName;
    private Integer containerPort;

}

