package com.lioneats.lioneats_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "azure.storage")
@Getter
@Setter
public class AzureStoragePropertiesConfig {

    private String connectionString;
    private String containerName;

}


