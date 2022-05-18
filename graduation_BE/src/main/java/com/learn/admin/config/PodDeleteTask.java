package com.learn.admin.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.PodInstance;
import com.learn.admin.bean.Record;
import com.learn.admin.mapper.PodInstanceMapper;
import com.learn.admin.mapper.RecordMapper;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1DeleteOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
平台定时任务，定期清理过期实例
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class PodDeleteTask {
    @Autowired
    PodInstanceMapper podInstanceMapper;
    @Autowired
    RecordMapper recordMapper;

    @Scheduled(fixedRate=40000)
    private void podDeleteTask(){
        if (BootWeb02Application.appContext == null) return;
        //获取k8s客户端
        ApiClient k8sClient = BootWeb02Application.appContext.getBean("master1",ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        //获取当前时间日期
        Date date = new Date();
        Date insDate = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("_______执行定时任务_________"+df.format(date));
        V1DeleteOptions body = new V1DeleteOptions();
        List<PodInstance> podInstances = podInstanceMapper.selectList(null);
        for(PodInstance ins : podInstances){
            try {
                insDate = df.parse(ins.getLife());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //如果实例过期则进入删除流程
            if (date.getTime() > insDate.getTime()){
                //删除实例Pod
                try {
                    api.deleteNamespacedPod(ins.getPodname(), ins.getUsername(), "true", null, null,
                            null, null, null);
                } catch (ApiException e) {
                    e.printStackTrace();
                }

                //删除实例绑定的Service
                try {
                    api.deleteNamespacedService(ins.getPodname(),ins.getUsername(), "true", null,
                            null, true, null, body);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                //删除数据库中的对应实例
                QueryWrapper<PodInstance> queryWrapper = new QueryWrapper<>();
                Map<String,String> queryMap = new HashMap<>();
                queryMap.put("username", ins.getUsername());
                queryMap.put("podname", ins.getPodname());
                queryWrapper.allEq(queryMap);
                podInstanceMapper.delete(queryWrapper);
                //释放成功，添加记录
                Record record = new Record(
                        ins.getUsername(),
                        df.format(date),
                        "因过期释放实例："+ins.getPodname()
                );
                recordMapper.insert(record);
                System.out.println("_________"+"实例过期："+ins.getPodname()+"________");
            }
        }
    }
}
