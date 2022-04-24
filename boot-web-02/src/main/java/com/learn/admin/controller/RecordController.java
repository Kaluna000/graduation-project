package com.learn.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.learn.admin.bean.Record;
import com.learn.admin.service.RecordService;
import com.learn.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@ResponseBody
@RestController
@Slf4j
public class RecordController {
    @Autowired
    RecordService recordService;

    @RequestMapping("/getUserRecord")
    public String getUserRecord(@RequestParam String username){
        Gson gson = new Gson();
        if (!"root".equals(username)) {
            QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            List<Record> list = recordService.list(queryWrapper);
            return gson.toJson(list);
        }else {
            List<Record> list = recordService.list();
            return gson.toJson(list);
        }
    }
}
