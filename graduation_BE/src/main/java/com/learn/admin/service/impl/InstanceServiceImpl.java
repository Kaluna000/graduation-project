package com.learn.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.bean.Instance;

import com.learn.admin.mapper.InstanceMapper;

import com.learn.admin.service.InstanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("InstanceService")
public class InstanceServiceImpl extends ServiceImpl<InstanceMapper, Instance> implements InstanceService {
    @Autowired
    InstanceMapper instanceMapper;

    @Override
    public List<Instance> InstanceList(String username){
        return instanceMapper.InstanceList(username);
    }

    @Override
    public List<Instance> RemainingInstance() {
        return instanceMapper.RemainingInstance();
    }


}
