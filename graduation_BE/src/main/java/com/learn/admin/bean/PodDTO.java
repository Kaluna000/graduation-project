package com.learn.admin.bean;

import lombok.Data;

@Data
public class PodDTO {
    private String podName;
    private String minCPU;
    private String maxCPU;
    private String minMemory;
    private String maxMemory;
    private String minEphemeralStorage;
    private String maxEphemeralStorage;
    private Float price;
    private Integer duration;
}
