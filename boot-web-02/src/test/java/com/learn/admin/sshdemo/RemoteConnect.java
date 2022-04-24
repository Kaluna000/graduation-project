package com.learn.admin.sshdemo;

public class RemoteConnect {
    private String ip;
    private String userName;
    private String password;

    public RemoteConnect(String ip, String userName, String password) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
    }

    public RemoteConnect() {
    }

    public String getIp() {
        return ip;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
