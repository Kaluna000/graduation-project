package com.learn.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.admin.bean.Instance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InstanceMapper extends BaseMapper<Instance> {
    public List<Instance> InstanceList(@Param("username") String username);
    public List<Instance> RemainingInstance();

}
