package com.learn.admin.sshdemo;

public class Test {
    private static String ip = "106.14.123.211";
    private static String userName = "root";
    private static String password = "LuoRan2000";
    public static void main(String[] args) {
        RemoteConnect connect = new RemoteConnect(ip, userName, password);
        System.out.println(RemoteSSH.login(connect));
    }
}
