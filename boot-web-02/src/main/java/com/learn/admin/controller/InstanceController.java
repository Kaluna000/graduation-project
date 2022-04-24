package com.learn.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.Instance;
import com.learn.admin.bean.OrderDTO;
import com.learn.admin.service.InstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@Slf4j
public class InstanceController {
    @Autowired
    InstanceService instanceService;

    @RequestMapping("/instanceList")
    public List<Instance> instanceList(@RequestParam String username)
    {
        return instanceService.InstanceList(username);
    }

    @RequestMapping("/remainingInstance")
    public List<Instance> remainingInstance(){
        System.out.println("请求获取剩余实例！！");
        return instanceService.RemainingInstance();
    }


    @RequestMapping("/instanceApplication")
    public CommonResult instanceApplication(@RequestBody OrderDTO orderDTO){
        System.out.println("请求绑定实例！！");
        try {
            String id = orderDTO.getInstanceId();
            String username = orderDTO.getUsername();
            Integer diskCapacity = orderDTO.getDiskCapacity();
            Integer storageNum = orderDTO.getStorageNum();
            Integer duration = orderDTO.getDuration();
            UpdateWrapper<Instance> updateWrapper = new UpdateWrapper<>();
            Instance instance = new Instance();
            instance.setOwner(username);
            instance.setDiskCapacity(diskCapacity);
            instance.setStorageNum(storageNum);
            instance.setDuration(duration);
            updateWrapper.eq("id",id);
            instanceService.update(instance,updateWrapper);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success("实例绑定成功！");
    }

    @RequestMapping("/instanceFree")
    public CommonResult instanceFree(@RequestParam String instanceName){
        System.out.println("请求释放实例！！");
        System.out.println(instanceName);
        try {
            UpdateWrapper<Instance> updateWrapper = new UpdateWrapper<>();
            Instance instance = new Instance();
            instance.setOwner("root");
            instance.setDiskCapacity(null);
            instance.setStorageNum(null);
            instance.setDuration(null);
            updateWrapper.eq("name",instanceName);
            instanceService.update(instance,updateWrapper);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success("实例释放成功！");
    }
}
