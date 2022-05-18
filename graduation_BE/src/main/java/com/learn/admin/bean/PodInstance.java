package com.learn.admin.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("podinstance")
public class PodInstance {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String username;
    @TableField
    private String podname;
    @TableField
    private String life;

    public PodInstance(String username, String podname, String life) {
        this.username = username;
        this.podname = podname;
        this.life = life;
    }
}
