package com.learn.admin.bean;

import io.kubernetes.client.custom.IntOrString;
import lombok.Data;

@Data
public class ServiceDTO {
    /**
     * apiVersion: v1
     * kind: Service
     * metadata:
     *   name: nginx-service	#Service 的名称
     *   labels:     	#Service 自己的标签
     *     app: nginx	#为该 Service 设置 key 为 app，value 为 nginx 的标签
     * spec:	    #这是关于该 Service 的定义，描述了 Service 如何选择 Pod，如何被访问
     *   selector:	    #标签选择器
     *     app: nginx	#选择包含标签 app:nginx 的 Pod
     *   ports:
     *   - name: nginx-port	#端口的名字
     *     protocol: TCP	    #协议类型 TCP/UDP
     *     port: 80	        #集群内的其他容器组可通过 80 端口访问 Service
     *     nodePort: 32600   #通过任意节点的 32600 端口访问 Service
     *     targetPort: 80	#将请求转发到匹配 Pod 的 80 端口
     *   type: NodePort	#Serive的类型，ClusterIP/NodePort/LoaderBalancer
     */
    private String serviceName;
    private String namespace;
    private String metadataLabelsApp;
    private String selectorApp;
    private String portsProtocol;
    private Integer portsPort;
    private Integer targetPort;

}

