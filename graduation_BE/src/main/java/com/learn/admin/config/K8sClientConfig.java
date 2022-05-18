package com.learn.admin.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileReader;

@Configuration
public class K8sClientConfig {

    @Bean
    public ApiClient master1() throws Exception{
        String kubeConfigPath = "D:\\MavenProject\\graduation_BE\\config";
        ApiClient master1 = ClientBuilder
                .kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath)))
                .build();
        return master1;
    }
}
