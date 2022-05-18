package com.learn.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.bean.User;

public interface UserService extends IService<User> {
    public User Login(String username,String password);
    public User SignUp(String username,String password);
}
