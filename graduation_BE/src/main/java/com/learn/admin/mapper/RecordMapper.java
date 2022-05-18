package com.learn.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.admin.bean.Record;
import com.learn.admin.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RecordMapper extends BaseMapper<Record>{
}
