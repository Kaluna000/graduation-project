package com.learn.admin.controller;

import com.google.gson.Gson;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.Deployment;
import com.learn.admin.bean.NodePortService;
import com.learn.admin.bean.Service;
import com.learn.admin.bean.ServiceDTO;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@Slf4j
public class ServiceController {

    @RequestMapping("/getAllService")
    public String getAllService(@RequestParam String instanceName){
        // new a CoreV1Api
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
//        CoreV1Api api = new CoreV1Api(k8sClient);
        CoreV1Api api = new CoreV1Api(k8sClient);
        System.out.println("请求获取ServiceList");
        // invokes the CoreV1Api client
        try {
            V1ServiceList list = api.listServiceForAllNamespaces(true, null,
                    null, null, null,  null, null, null,null,null);
            List<Service> serviceList = new ArrayList<>();
            for (int i = 0; i < list.getItems().size(); i++) {
                V1Service item = list.getItems().get(i);
                String name = item.getMetadata().getName();
                String namespace =item.getMetadata().getNamespace();
                String type = item.getSpec().getType();
                String clusterIp = item.getSpec().getClusterIP();
                String port = item.getSpec().getPorts().get(0).getPort()+"/"+item.getSpec().getPorts().get(0).getProtocol();
                String matchLabelsApp;
                if (item.getSpec().getSelector() != null)
                    matchLabelsApp = item.getSpec().getSelector().get("app");
                else matchLabelsApp = null;
//                List<V1ServicePort> portList= new ArrayList<>();
//                portList = item.getSpec().getPorts();
//                System.out.println(portList.size());
//                for (int j = 0; j < portList.size(); j++) {
//                    System.out.println("&&&&&&&&&"+portList.get(j));
//                }
                serviceList.add(new Service(name,type,namespace,matchLabelsApp,clusterIp,port));
            }
            return new Gson().toJson(serviceList);
        } catch (ApiException e) {
            log.error("获取Servicelist异常:" + e.getResponseBody(), e);
        }
        return null;
    }

    /**
     * 创建一个service
     * @param instanceName
     * @param serviceDTO
     * @return
     */
    @PostMapping("/createNamespacedService")
    public CommonResult createNamespacedService(@RequestParam String instanceName,
                                                @RequestBody ServiceDTO serviceDTO) {

        System.out.println("请求创建Service");
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
//        CoreV1Api api = new CoreV1Api(k8sClient);
        CoreV1Api api = new CoreV1Api(k8sClient);
        Gson gson = new Gson();
        V1Service result;
        V1LabelSelector v1LabelSelector = new V1LabelSelector();

        //selector
        Map<String,String> matchSelector= new HashMap<String,String>();
        matchSelector.put("app",serviceDTO.getSelectorApp());

        //labels
        Map<String,String> matchLabels = new HashMap<>();
        matchLabels.put("app", serviceDTO.getMetadataLabelsApp());


        V1Service svc = new V1ServiceBuilder()
                .withNewMetadata()
                .withName(serviceDTO.getServiceName())
                .endMetadata()
                .withNewSpec()
                .addNewPort()
                .withProtocol(serviceDTO.getPortsProtocol())
                .withPort(serviceDTO.getPortsPort())
                .withTargetPort(new IntOrString(serviceDTO.getTargetPort()))
                .endPort()
                .withSelector(matchSelector)
                .endSpec()
                .build();

        // Deployment and StatefulSet is defined in apps/v1, so you should use AppsV1Api instead of CoreV1API

        try {
            result = api.createNamespacedService(serviceDTO.getNamespace(), svc, "true", null, null);
            System.out.println(result);
        } catch (ApiException e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(gson.toJson(result));

    }

    /**
     * 删除对应的Service
     * @param instanceName
     *
     */
    @RequestMapping("/deleteService")
    public CommonResult deleteService(@RequestParam String instanceName,@RequestBody Service service) throws ApiException {
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        CoreV1Api api = new CoreV1Api(k8sClient);
        Gson gson = new Gson();
        System.out.println(instanceName);
        System.out.println(service);
        try {
            V1Status v1Status = api.deleteNamespacedService(service.getName(),service.getNamespace(),null,null,null,null,null,null);
        }catch (ApiException e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success("删除成功");
    }

    /*
    获取具有名称空间的service
     */
    @RequestMapping("/getNamespacedService")
    public String getNamespacedService(@RequestParam String instanceName,@RequestParam String username){
        // new a CoreV1Api
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
//        CoreV1Api api = new CoreV1Api(k8sClient);
        CoreV1Api api = new CoreV1Api(k8sClient);
        System.out.println("请求获取ServiceList");
        // invokes the CoreV1Api client
        try {
            V1ServiceList list = api.listNamespacedService(username, null,
                    null, null, null,  null, null, null,null,null,null);
            List<NodePortService> serviceList = new ArrayList<>();
            for (int i = 0; i < list.getItems().size(); i++) {
                V1Service item = list.getItems().get(i);
                String name = item.getMetadata().getName();
                String namespace =item.getMetadata().getNamespace();
                String type = item.getSpec().getType();
                String clusterIp = item.getSpec().getClusterIP();
                String port = item.getSpec().getPorts().get(0).getPort()+"/"+item.getSpec().getPorts().get(0).getProtocol();
                String matchLabelsApp;
                if (item.getSpec().getSelector() != null)
                    matchLabelsApp = item.getSpec().getSelector().get("app");
                else matchLabelsApp = null;
//                List<V1ServicePort> portList= new ArrayList<>();
//                portList = item.getSpec().getPorts();
//                System.out.println(portList.size());
//                for (int j = 0; j < portList.size(); j++) {
//                    System.out.println("&&&&&&&&&"+portList.get(j));
//                }
                Integer nodePort = item.getSpec().getPorts().get(0).getNodePort();
                serviceList.add(new NodePortService(name,type,namespace,matchLabelsApp,clusterIp,port,nodePort));
            }
            return new Gson().toJson(serviceList);
        } catch (ApiException e) {
            log.error("获取Servicelist异常:" + e.getResponseBody(), e);
        }
        return null;
    }
}
