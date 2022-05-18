package com.learn.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.bean.Account;
import com.learn.admin.mapper.AccountMapper;
import com.learn.admin.service.AccountService;
import org.springframework.stereotype.Service;

@Service("AccountService")
public class AccountServiceImpl  extends ServiceImpl<AccountMapper, Account> implements AccountService {
}
