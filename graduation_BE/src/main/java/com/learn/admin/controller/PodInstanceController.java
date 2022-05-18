package com.learn.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.Account;
import com.learn.admin.bean.Bill;
import com.learn.admin.bean.PodInstance;
import com.learn.admin.mapper.AccountMapper;
import com.learn.admin.mapper.BillMapper;
import com.learn.admin.mapper.PodInstanceMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@Slf4j
public class PodInstanceController {
    @Autowired
    PodInstanceMapper podInstanceMapper;
    @Autowired
    BillMapper billMapper;
    @Autowired
    AccountMapper accountMapper;
    @RequestMapping(value = "/getInstanceLife")
    public String getInstanceLife(@RequestParam String username){
        Gson gson = new Gson();
        QueryWrapper<PodInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<PodInstance> podInstances = podInstanceMapper.selectList(queryWrapper);
        return gson.toJson(podInstances);
    }
    @RequestMapping(value = "/renew")
    public CommonResult renew(@RequestParam String username,@RequestParam String podName,@RequestParam Float price,@RequestParam Integer duration){
        //验证余额是否充足
        QueryWrapper<Account> accountWrapper = new QueryWrapper<>();
        accountWrapper.eq("username",username);
        Account account = accountMapper.selectOne(accountWrapper);
        if (account.getRemain() < price){
            System.out.println("余额不足！");
            return CommonResult.failed("余额不足，请充值！");
        }
        //余额充足
        QueryWrapper<PodInstance> queryWrapper = new QueryWrapper<>();
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("username",username);
        queryMap.put("podname",podName);
        queryWrapper.allEq(queryMap);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PodInstance podInstance = podInstanceMapper.selectOne(queryWrapper);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(df.parse(podInstance.getLife()));
            calendar.add(Calendar.MONTH, duration);
            podInstance.setLife(df.format(calendar.getTime()));
            podInstanceMapper.update(podInstance, queryWrapper);
            //扣除费用
            account.setRemain(account.getRemain()-price);
            accountMapper.update(account,accountWrapper);
            //添加账单
            Bill bill = new Bill(
                    username,
                    df.format(new java.util.Date()),
                    "-"+price,
                    "续费实例: "+podName+",费用为： "+price+"元"
            );
            billMapper.insert(bill);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.failed("续费失败！");
        }
        return CommonResult.success("续费成功！");
    }
}
