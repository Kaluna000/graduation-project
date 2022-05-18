package com.learn.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.*;
import com.learn.admin.mapper.AccountMapper;
import com.learn.admin.mapper.BillMapper;
import com.learn.admin.mapper.PodInstanceMapper;
import com.learn.admin.mapper.RecordMapper;
import com.learn.admin.service.RecordService;
import com.learn.admin.service.UserService;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.proto.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@ResponseBody
@RestController
@Slf4j
public class ApplicationController {
    /*
    root@slave001:~/yaml# cat case1-pod-memory-limit.yml
#apiVersion: extensions/v1beta1
apiVersion: apps/v1
kind: Deployment
metadata:
  name: limit-test-deployment
  namespace: chuan
spec:
  replicas: 1
  selector:
    matchLabels: #rs or deployment
      app: limit-test-pod
#    matchExpressions:
#      - {key: app, operator: In, values: [ng-deploy-80,ng-rs-81]}
  template:
    metadata:
      labels:
        app: limit-test-pod
    spec:
      containers:
      - name: limit-test-container
        image: lorel/docker-stress-ng
        resources:
          limits:
            memory: "110Mi"
            cpu: 200m
          requests:
            memory: "100Mi"
        #command: ["stress"]
        args: ["--vm", "2", "--vm-bytes", "256M"]
      #nodeSelector:
      #  env: group1
     */
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    BillMapper billMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    PodInstanceMapper podInstanceMapper;

    //申请Pod实例
    @RequestMapping(value = "/podApplication")
    public CommonResult podApplication(@RequestParam String instanceName,@RequestParam String username,@RequestBody PodDTO podDTO){
        System.out.println("请求创建pod实例！！");
        //验证余额是否充足
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Account account = accountMapper.selectOne(queryWrapper);
        if (account.getRemain() < podDTO.getPrice()){
            System.out.println("余额不足！");
            return CommonResult.failed("余额不足，请充值！");
        }
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        Gson gson = new Gson();
        System.out.println(podDTO);
        //将podName转换为小写，否则k8s会报错
//        podDTO.setPodName(podDTO.getPodName().toLowerCase(Locale.ROOT));
        //创建pod
        List<V1ContainerPort> portList = new ArrayList<>();
        V1ContainerPort port = new V1ContainerPort();
        port.setContainerPort(22);
        portList.add(port);
        Map<String,String> labels = new HashMap<>();
        labels.put("app",podDTO.getPodName());
        //启动时修改root密码
//        List<String> command = new ArrayList<>();
//        command.add("sed -i '/^root/c/root:$6$ujVh3Uat$/bcJ5F/f/JSKw40ZTmVrbrBgzlY32Foq4GFO6cRinlWCgjupWriUJu/fpQhphf0gSaOEr4dZ.8zZFTNEbDgUV/:19103:0:99999:7:::' /etc/shadow");
        //配置资源限制
        Map<String, Quantity> limits = new HashMap<>();
        Map<String, Quantity> requests = new HashMap<>();
        limits.put("cpu",new Quantity(podDTO.getMaxCPU()+"m"));
        limits.put("memory",new Quantity(podDTO.getMaxMemory()+"Mi"));
        limits.put("ephemeral-storage",new Quantity(podDTO.getMaxEphemeralStorage()+"Gi"));
        requests.put("cpu",new Quantity(podDTO.getMinCPU()+"m"));
        requests.put("memory",new Quantity(podDTO.getMinMemory()+"Mi"));
        requests.put("ephemeral-storage",new Quantity(podDTO.getMinEphemeralStorage()+"Gi"));
        V1ResourceRequirements resourceRequirements = new V1ResourceRequirements();
        resourceRequirements.setLimits(limits);
        resourceRequirements.setRequests(requests);
        V1Pod v1Pod = new V1PodBuilder()
                .withApiVersion("v1")
                .withNewKind("Pod")
                .withNewMetadata()
                .withName(podDTO.getPodName())
                .withNamespace(username)
                .withLabels(labels)
                .endMetadata()
                .withNewSpec()
                .withContainers(
                        new V1Container()
                                .name(podDTO.getPodName())
                                .image("amadeus000/mycentosssh:v1.0")
                                .imagePullPolicy("IfNotPresent")
                                .ports(portList)
                                .resources(resourceRequirements)
                )
                .endSpec()
                .build();

        try {
            api.createNamespacedPod(username,v1Pod,null,null,null);
        } catch (ApiException e) {

            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        //创建该pod对应的service
        V1Service result;

        //selector
        Map<String,String> matchSelector= new HashMap<String,String>();
        matchSelector.put("app",podDTO.getPodName());

        //labels
        Map<String,String> matchLabels = new HashMap<>();
        matchLabels.put("app", podDTO.getPodName());

        V1Service svc = new V1ServiceBuilder()
                .withNewMetadata()
                .withName(podDTO.getPodName())
                .endMetadata()
                .withNewSpec()
                .withType("NodePort")
                .addNewPort()
                .withPort(22)
                .withTargetPort(new IntOrString(22))
                .endPort()
                .withSelector(matchSelector)
                .endSpec()
                .build();
        try {
            result = api.createNamespacedService(username, svc, "true", null, null);
            System.out.println(result);
        } catch (ApiException e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        //同步至数据库，记录到期时间
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //设置当前日期
        calendar.setTime(date);
        //定位到到期时间
        calendar.add(Calendar.MONTH,+podDTO.getDuration());
        PodInstance podInstance = new PodInstance(
          username,
          podDTO.getPodName(),
          df.format(calendar.getTime())
        );
        podInstanceMapper.insert(podInstance);

        //申请成功，添加至记录
        Record record = new Record(
                username,
                df.format(date),
                "申请实例："+podDTO.getPodName()+", 资源配额：CPU: 最低"+podDTO.getMinCPU()+"m,最高"
                +podDTO.getMaxCPU()+"m; 内存: 最低"+podDTO.getMinMemory()+"Mi,最高"+podDTO.getMaxMemory()
                +"Mi; 磁盘存储: 最低"+podDTO.getMinEphemeralStorage()+"Gi,最高"+podDTO.getMaxEphemeralStorage()+"Gi"
        );
        recordMapper.insert(record);

        //扣除费用
        account.setRemain(account.getRemain()-podDTO.getPrice());
        accountMapper.update(account,queryWrapper);

        //添加账单
        Bill bill = new Bill(
                username,
                df.format(date),
                "-"+podDTO.getPrice(),
                "申请实例: "+podDTO.getPodName()+",费用为： "+podDTO.getPrice()+"元"
        );
        billMapper.insert(bill);
        return CommonResult.success(gson.toJson(result));
    }

    /**
     * 根据name、namespace删除pod TODO:能删除，会报错 Expected a string but was BEGIN_OBJECT
     * @param podName
     * @param username
     * @return
     */
    @RequestMapping("/deleteNamespacedPod")
    public CommonResult deleteNamespacePod(@RequestParam String instanceName,@RequestParam String podName, @RequestParam String username) {
        System.out.println("请求释放pod实例！！");
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        V1DeleteOptions body = new V1DeleteOptions();
        //删除实例Pod
        try {
            api.deleteNamespacedPod(podName, username, "true", null, null,
                    null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }

        //删除实例绑定的Service
        try {
            api.deleteNamespacedService(podName,username, "true", null,
                    null, true, null, body);
        } catch (ApiException e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        //释放成功,添加至记录
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Record record = new Record(
                username,
                df.format(date),
                "释放实例："+podName
        );
        recordMapper.insert(record);
        return CommonResult.success("实例释放成功!");
    }

}
