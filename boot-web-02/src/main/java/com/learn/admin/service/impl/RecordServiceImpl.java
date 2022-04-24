package com.learn.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.bean.Record;
import com.learn.admin.mapper.RecordMapper;
import com.learn.admin.service.RecordService;
import org.springframework.stereotype.Service;

@Service("RecordService")
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
}
