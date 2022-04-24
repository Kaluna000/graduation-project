package com.learn.admin.bean;

import lombok.Data;

@Data
public class OrderDTO {
    private String instanceId;
    private String username;
    private Integer diskCapacity;
    private Integer storageNum;
    private Integer duration;
}
