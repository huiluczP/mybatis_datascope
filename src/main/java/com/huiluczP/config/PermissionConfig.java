package com.huiluczP.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

// 从配置文件中读取permission层次范围
@Configuration
@ConfigurationProperties(prefix = "permission")
@EnableConfigurationProperties(PermissionConfig.class)
public class PermissionConfig {
    private Map<String, String> permissionMap = new HashMap<>();

    public Map<String, String> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(Map<String, String> permissionMap) {
        this.permissionMap = permissionMap;
    }
}
