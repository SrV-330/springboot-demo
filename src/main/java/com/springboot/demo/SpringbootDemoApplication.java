package com.springboot.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

@SpringBootApplication
@Slf4j
public class SpringbootDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);
        log.info("springboot demo");
        Object obj = context.getBeanFactory().getBean("person");
        log.info(obj.toString());
        String[] beanDefNames = context.getBeanDefinitionNames();
        if (beanDefNames == null) return;
        log.info("BeanDefinitionNames: ");
        log.info("=============================================");
        for (String beanDefName : beanDefNames) {
            log.info(beanDefName);
        }

    }

}
