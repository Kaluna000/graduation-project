package com.learn.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.bean.User;
import com.learn.admin.mapper.UserMapper;
import com.learn.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User Login(String username,String password){
        return userMapper.Login(username,password);
    }

   @Override
   public User SignUp(String username,String password){
        return userMapper.SignUp(username,password);
   }
}
