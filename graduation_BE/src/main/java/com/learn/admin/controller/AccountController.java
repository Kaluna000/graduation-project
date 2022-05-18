package com.learn.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.Account;
import com.learn.admin.bean.Bill;
import com.learn.admin.bean.Record;
import com.learn.admin.mapper.AccountMapper;
import com.learn.admin.mapper.BillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ResponseBody
@RestController
@Slf4j
public class AccountController {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    BillMapper billMapper;

    @RequestMapping(value ="/getUserRemain")
    public String getUserAccount(@RequestParam String username){
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Account account = accountMapper.selectOne(wrapper);
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        return decimalFormat.format(account.getRemain());
    }

    @RequestMapping("/recharge")
    public CommonResult recharge(@RequestParam String username,@RequestParam Float value){
        System.out.println("请求充值！");
        try {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("username",username);
            Account account = accountMapper.selectOne(wrapper);
            Float newRemain = account.getRemain()+value;
            account.setRemain(newRemain);
            accountMapper.update(account,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("充值失败："+e.getMessage());
        }
        //充值成功，记录账单
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Bill bill = new Bill(
                username,
                df.format(date),
                "+"+value,
                "充值"+value+"元"
        );
        billMapper.insert(bill);
        return CommonResult.success("充值成功！账户余额增加"+value+"元");
    }

    @RequestMapping("/getBill")
    public String getBill(@RequestParam String username){
        Gson gson = new Gson();
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<Bill> list = billMapper.selectList(queryWrapper);
        return gson.toJson(list);
    }

    @RequestMapping("/getBillNum")
    public String getBillNum(@RequestParam String username){
        Gson gson = new Gson();
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Long billNum = billMapper.selectCount(queryWrapper);
        return billNum+"";
    }

}
