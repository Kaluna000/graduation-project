package com.learn.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.admin.bean.User;
import com.learn.admin.mapper.UserMapper;
import com.learn.admin.service.UserService;
import io.kubernetes.client.custom.Quantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;

@SpringBootTest
class BootWeb02ApplicationTests {
    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq("username","root");
//        User one = userService.getOne(userQueryWrapper);
//        System.out.println(one.getUsername());
//        System.out.println(one.getPassword());
        Quantity quantity1 = new Quantity("500m");
        Quantity quantity2 = new Quantity("1024Mi");
        Quantity quantity3 = new Quantity("3Gi");
        System.out.println(quantity1);
        System.out.println(quantity2);
        System.out.println(quantity3);
//        System.out.println(new BigDecimal("500"));
    }

}
