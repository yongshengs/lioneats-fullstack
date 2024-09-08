package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.config.AzureStoragePropertiesConfig;
import com.lioneats.lioneats_backend.config.GoogleApiPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({GoogleApiPropertiesConfig.class, AzureStoragePropertiesConfig.class})
public class LioneatsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LioneatsBackendApplication.class, args);
    }

}
