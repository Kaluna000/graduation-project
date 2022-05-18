package com.learn.admin.controller;

import com.learn.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
@Slf4j
public class RootUserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getUserNum")
    public Long getUserNum(){
        System.out.println(userService.count());
        return userService.count();
    }
}
