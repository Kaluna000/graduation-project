package com.learn.admin.controller;

import com.google.gson.Gson;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.Pod;
import com.learn.admin.bean.PodDTO;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@Slf4j
public class PodController {

    /*
    获取当前实例的所有pod
     */
    @RequestMapping(value = "/getAllPods")
    public String getAllPodList(@RequestParam String instanceName){
        // new a CoreV1Api
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        System.out.println("请求获取podList");
        // invokes the CoreV1Api client
        try {
            V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
            //对获取到的Podlist进行再封装，保留需要的信息
            List<Pod> podList = new ArrayList();
            for (int i = 0; i < list.getItems().size(); i++) {
                V1Pod item = list.getItems().get(i);
                String name = item.getMetadata().getName();
                String namespace =item.getMetadata().getNamespace();
                String status =item.getStatus().getPhase();
                String ip = item.getStatus().getPodIP();
                String startTime = item.getStatus().getStartTime().toString();
                podList.add(new Pod(name,namespace,status,ip,startTime));
            }
//            System.out.println(list.getItems().get(1).getStatus().getStartTime());
            return new Gson().toJson(podList);
//            return new GsonBuilder().setPrettyPrinting().create().toJson(list);
        } catch (ApiException e) {
            log.error("获取podlist异常:" + e.getResponseBody(), e);
        }
        return null;
    }

    /*
    获取指定namespace下的pod
     */
    @RequestMapping(value = "/getNamespacedPods")
    public String getNamespacedPod(@RequestParam String instanceName,@RequestParam String username){
        // new a CoreV1Api
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        System.out.println("请求获取namespaced podList");
        // invokes the CoreV1Api client
        try {
            //获取podlist
            V1PodList list = api.listNamespacedPod(username,null,null,null,null,null,null,null,null,null,null);
            //对获取到的Podlist进行再封装，保留需要的信息
            List<Pod> podList = new ArrayList();
            for (int i = 0; i < list.getItems().size(); i++) {
                V1Pod item = list.getItems().get(i);
                String name = item.getMetadata().getName();
                String namespace =item.getMetadata().getNamespace();
                String status =item.getStatus().getPhase();
                String ip = item.getStatus().getPodIP();
                String startTime = item.getStatus().getStartTime().toString();
                podList.add(new Pod(name,namespace,status,ip,startTime));
            }
//            System.out.println(list.getItems().get(1).getStatus().getStartTime());
            return new Gson().toJson(podList);
//            return new GsonBuilder().setPrettyPrinting().create().toJson(list);
        } catch (ApiException e) {
            log.error("获取podlist异常:" + e.getResponseBody(), e);
        }
        return null;
    }

    /*
    创建具有namespace的pod
     */
    @RequestMapping(value = "/createNamespacedPod")
    public CommonResult createNamespacedPod(@RequestParam String instanceName, @RequestParam String username, @RequestBody PodDTO podDTO){
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        Gson gson = new Gson();
        V1Pod namespacedPod;
        System.out.println("请求创建pod");
        List<V1ContainerPort> portList = new ArrayList<>();
        V1ContainerPort port = new V1ContainerPort();
        port.setContainerPort(22);
        port.setHostPort(50022);
        port.protocol("TCP");
        portList.add(port);
        V1Pod v1Pod = new V1PodBuilder()
                .withApiVersion("v1")
                .withNewKind("Pod")
                .withNewMetadata()
                .withName(podDTO.getPodName())
                .withNamespace(username)
                .endMetadata()
                .withNewSpec()
                .withContainers(
                        new V1Container()
                                .name(podDTO.getPodName())
                                .image("amadeus000/mycentosssh:v1.0")
                                .imagePullPolicy("IfNotPresent")
                                .ports(portList)
                )
                .endSpec()
                .build();
        try {
            namespacedPod = api.createNamespacedPod(username, v1Pod, null, null, null);
        } catch (ApiException e) {
            System.out.println(e.getMessage());
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(gson.toJson(namespacedPod));
    }


}
