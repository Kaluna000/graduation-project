package com.learn.admin.bean;

import lombok.Data;

/**
 * websocket消息结构
 */
@Data
public class MessageData {

    /**
     * 要执行的操作
     */
    private String operate;

    /**
     * 服务期主机地址
     */
    private String host;

    /**
     * ssh端口
     */
    private Integer port = 22;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录用户密码
     */
    private String password;

    /**
     * ssh执行的命令
     */
    private String command = "";

    /**
     * 终端窗口展示列数
     * 主要用于解决前端命令长度不一致问题
     */
    private String cols;

    /**
     * 终端窗口展示行数
     */
    private String rows;

    /**
     * 是否加密
     */
    private String encrypt;
}
