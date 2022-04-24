package com.learn.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.admin.bean.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper  extends BaseMapper<Account> {
}
