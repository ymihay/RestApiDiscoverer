package com.smartbear.alertsite.configuration;

import com.smartbear.alertsite.service.DiscoverResourcesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * Created by yanamikhaylenko on 5/14/15.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.smartbear.alertsite.*")
@SpringBootApplication
public class Application {
    private DiscoverResourcesService discoverResourcesService = new DiscoverResourcesService("http://petstore.swagger.io/api/api-docs");
            //new DiscoverResourcesService("http://petstore.swagger.io/v2/swagger.json");

    public static void main(String[] args) {
        /*ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }*/
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "discoverService")
    public DiscoverResourcesService getDiscoverService() {
        return discoverResourcesService;
    }

}

