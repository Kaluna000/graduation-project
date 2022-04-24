package com.learn.admin.controller;

import com.learn.admin.BootWeb02Application;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
@Slf4j
public class ClusterController {

    public String getCPUMsg(@RequestParam String instanceName){
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        return "";
    }
}
