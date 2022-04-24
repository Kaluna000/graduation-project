package com.learn.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.bean.Instance;

import java.util.List;


public interface InstanceService extends IService<Instance> {
    public List<Instance> InstanceList(String username);
    public List<Instance> RemainingInstance();
}