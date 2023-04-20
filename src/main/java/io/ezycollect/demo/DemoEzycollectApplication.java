package io.ezycollect.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(info =
@Info(title = "Demo API EzyCollect", version = "1.0", description = "Documentation Demo API EzyCollect v1.0")
)
public class DemoEzycollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoEzycollectApplication.class, args);
    }

}
