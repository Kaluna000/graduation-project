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
@TableName("account")
public class Account {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String username;
    @TableField
    private Float remain;

    public Account(String username, Float remain) {
        this.username = username;
        this.remain = remain;
    }
}
