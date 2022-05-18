package com.learn.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.bean.PodInstance;
import com.learn.admin.mapper.PodInstanceMapper;
import com.learn.admin.service.PodInstanceService;
import org.springframework.stereotype.Service;

@Service("PodInstanceService")
public class PodInstanceServiceImpl extends ServiceImpl<PodInstanceMapper, PodInstance> implements PodInstanceService {
}
