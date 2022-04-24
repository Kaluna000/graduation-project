package com.learn.admin.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("bill")
public class Bill {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String username;
    @TableField
    private String time;
    @TableField
    private String cost;
    @TableField
    private String message;

    public Bill(String username, String time, String cost,String message) {
        this.username = username;
        this.time = time;
        this.cost = cost;
        this.message = message;
    }
}
