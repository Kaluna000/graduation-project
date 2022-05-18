package com.learn.admin.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("instancetable")
public class Instance {
    @TableField
    private Integer id;
    @TableField
    private String name;
    @TableField
    private String ip;
    @TableField
    private String cpu;
    @TableField
    private String memory;
    @TableField
    private boolean running;
    @TableField
    private String owner;
    @TableField
    private Integer bandwidth;
    @TableField
    private Integer price;
    @TableField
    private Integer diskCapacity;
    @TableField
    private Integer storageNum;
    @TableField
    private Integer duration;
}
