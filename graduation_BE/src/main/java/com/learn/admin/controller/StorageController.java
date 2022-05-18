package com.learn.admin.controller;

import com.learn.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StorageController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/storage/storage")
    public String storagePage(){
        return "storage/storage";
    }
}
