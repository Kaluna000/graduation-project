package com.learn.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.admin.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public User Login(@Param("username") String username, @Param("password") String password);
    public User SignUp(@Param("username") String username,@Param("password") String password);
}

