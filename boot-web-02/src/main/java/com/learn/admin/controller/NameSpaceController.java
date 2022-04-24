package com.learn.admin.controller;

import com.google.gson.Gson;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.bean.Pod;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@Slf4j
public class NameSpaceController {
    /*
   获取当前实例的所有namespace
    */
    @RequestMapping(value = "/getAllNamespace")
    public String getAllPodList(@RequestParam String instanceName){
        // new a CoreV1Api
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        System.out.println("请求获取namespaceList");
        // invokes the CoreV1Api client
        try {
            V1NamespaceList list = api.listNamespace(null,false,null,null,null,null,null,null,null,false);
            //对获取到的Podlist进行再封装，保留需要的信息
            List<String> namespaceList = new ArrayList();
            for (int i = 0; i < list.getItems().size(); i++) {
                V1Namespace item = list.getItems().get(i);
                namespaceList.add(item.getMetadata().getName());
            }
//            System.out.println(list.getItems().get(1).getStatus().getStartTime());
            return new Gson().toJson(namespaceList);
//            return new GsonBuilder().setPrettyPrinting().create().toJson(list);
        } catch (ApiException e) {
            log.error("获取namespacelist异常:" + e.getResponseBody(), e);
        }
        return null;
    }
}
