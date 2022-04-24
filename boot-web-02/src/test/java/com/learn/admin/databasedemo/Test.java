//package com.learn.admin.databasedemo;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.learn.admin.bean.User;
//import com.learn.admin.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//
//public class Test {
//    @Autowired
//    static UserService userService;
//
//
//    public static void main(String[] args) {
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq("user_name","root");
//        User one = userService.getOne(userQueryWrapper);
//        System.out.println(one.getUserName());
//        System.out.println(one.getPassword());
//    }
//}
