package com.learn.admin.controller;

import com.google.gson.Gson;
import com.learn.admin.BootWeb02Application;
import com.learn.admin.api.CommonResult;
import com.learn.admin.bean.Deployment;
import com.learn.admin.bean.DeploymentDTO;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@Slf4j
public class DeploymentController {

    @RequestMapping("/getAllDeployments")
    public String getAllDeployment(@RequestParam String instanceName){
        // new a CoreV1Api
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
//        CoreV1Api api = new CoreV1Api(k8sClient);
        AppsV1Api api = new AppsV1Api(k8sClient);
        System.out.println("请求获取DeploymentList");
        // invokes the CoreV1Api client
        try {
            V1DeploymentList list = api.listDeploymentForAllNamespaces(null, null, null,
                    null, null, "true", null, null,null,false);
            List<Deployment> deploymentList = new ArrayList<>();
            for (int i = 0; i < list.getItems().size(); i++) {
                V1Deployment item = list.getItems().get(i);
                String name = item.getMetadata().getName();
                String namespace =item.getMetadata().getNamespace();
                Integer replicas = item.getStatus().getReplicas();
                Integer readyReplicas = item.getStatus().getReadyReplicas();
                String matchLabelsApp;
                if (item.getSpec().getSelector().getMatchLabels() != null){
                    matchLabelsApp = item.getSpec().getSelector().getMatchLabels().get("app");
                }else matchLabelsApp = null;
                deploymentList.add(new Deployment(name,namespace,replicas,readyReplicas,matchLabelsApp));
            }
            return new Gson().toJson(deploymentList);
        } catch (ApiException e) {
            log.error("获取deploymentlist异常:" + e.getResponseBody(), e);
        }
        return null;
    }


    /**
     * 创建一个deployment
     * @param deploymentDTO
     * @return
     */
    @PostMapping("/createNamespacedDeployment")
    public CommonResult createNamespacedDeployment(@RequestParam String instanceName,@RequestBody DeploymentDTO deploymentDTO) {
        System.out.println("请求创建Deployment！！！");
        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        Gson gson = new Gson();
        AppsV1Api apiInstance = new AppsV1Api(k8sClient);
        V1Deployment result;

        // labels
        Map<String,String> matchLabels = new HashMap<>();
        matchLabels.put("app", deploymentDTO.getMetadataLabelsApp());

        // ports
        List<V1ContainerPort> portList = new ArrayList<>();
        V1ContainerPort port = new V1ContainerPort();
        port.setName(deploymentDTO.getPortName());
        port.setContainerPort(deploymentDTO.getContainerPort());
        portList.add(port);

        // 使用对象封装deployment
        V1Deployment body = new V1DeploymentBuilder()
                .withApiVersion("apps/v1")
                .withKind("Deployment")
                .withNewMetadata()
                .withName(deploymentDTO.getDeploymentName())
                .withNamespace(deploymentDTO.getNamespace())
                .endMetadata()
                .withNewSpec()
                .withReplicas(deploymentDTO.getReplicas())
                .withNewSelector()
                .withMatchLabels(matchLabels)
                .endSelector()
                .withNewTemplate()
                .withNewMetadata()
                .withLabels(matchLabels)
                .endMetadata()
                .withNewSpec()
                .withContainers(
                        new V1Container()
                                .name(deploymentDTO.getMetadataLabelsApp())
                                .image(deploymentDTO.getImage())
                                .imagePullPolicy("IfNotPresent")
                                .ports(portList)
                )
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();
        try {
            result = apiInstance.createNamespacedDeployment(
                    deploymentDTO.getNamespace(),
                    body,
                    "true",
                    null,
                    null);
            System.out.println(result);
        } catch (ApiException e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(gson.toJson(result));
    }

    /**
     * 删除对应的deployment
     * @param instanceName
     *
     */
    @RequestMapping("/deleteDeployment")
     public CommonResult deleteDeployment(@RequestParam String instanceName,@RequestBody Deployment deployment) throws ApiException {

        ApiClient k8sClient = BootWeb02Application.appContext.getBean(instanceName,ApiClient.class);
        AppsV1Api api = new AppsV1Api(k8sClient);
        Gson gson = new Gson();
        System.out.println(instanceName);
        System.out.println(deployment);
        try {
            V1Status v1Status = api.deleteNamespacedDeployment(deployment.getName(),deployment.getNamespace(),null,null,null,null,null,null);
        }catch (ApiException e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success("删除成功");
    }

}
