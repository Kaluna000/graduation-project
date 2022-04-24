package com.learn.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.api.CommonResult;

import com.learn.admin.bean.Account;
import com.learn.admin.bean.User;
import com.learn.admin.mapper.AccountMapper;
import com.learn.admin.service.UserService;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;


@ResponseBody
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    AccountMapper accountMapper;

    @RequestMapping(value = "/admin/login")
    public CommonResult login(@RequestBody User user) {

        //String username= request.getParameter("username");
        //String password= request.getParameter("password");
        String username=user.getUsername();
        String password=user.getPassword();

        //输出测试
        System.out.println("$$$$$$$$$");
        System.out.println(username);
        System.out.println(password);

        user=userService.Login(username,password);
        if(user!=null){
            return CommonResult.success(username);
        }
        else{
            return CommonResult.validateFailed("我是后端Controller,登陆失败");
        }
    }

    @RequestMapping(value = "/admin/signUp")
    public CommonResult signUp(@RequestBody User user){
        String username=user.getUsername();
        String password=user.getPassword();
        //输出测试
        System.out.println("$$$$$$$$$");
        System.out.println(username);
        System.out.println(password);
        if (username == "root"){
            return CommonResult.failed("用户名不能为root！");
        }
        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("username",username);
        if (userService.getOne(wrapper) != null){
            return CommonResult.failed("用户名重复，请重新输入！");
        }else {
            User newUser = userService.SignUp(username,password);
            //为新用户注册namespace
            try {
                ApiClient k8sClient = BootWeb02Application.appContext.getBean("master1",ApiClient.class);
                CoreV1Api api = new CoreV1Api(k8sClient);
                V1Namespace v1Namespace = new V1NamespaceBuilder()
                        .withNewMetadata()
                        .withName(username)
                        .endMetadata()
                        .build();
                api.createNamespace(v1Namespace, null, null, null);
            } catch (ApiException e) {
                e.printStackTrace();
                return CommonResult.failed("注册namespace异常！");
            }

            //为新用户注册账户
            try {
                Account account = new Account(username,(float) 0);
                accountMapper.insert(account);
            } catch (Exception e) {
                e.printStackTrace();
                return CommonResult.failed("新用户创建账户异常！"+e.getMessage());
            }

            return CommonResult.success(username);
        }
    }
}

