package com.learn.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.admin.bean.Bill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {
}
