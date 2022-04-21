package com.springboot.demo;

import com.springboot.demo.bean.Car;
import com.springboot.demo.bean.Pet;
import com.springboot.demo.config.DemoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class SpringbootDemoApplication {

    public static void main(String[] args) {

        //ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);
        SpringApplication application = new SpringApplication(SpringbootDemoApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        ConfigurableApplicationContext context = application.run(args);

        log.info("springboot demo");
        Object obj = context.getBeanFactory().getBean("person");
        log.info(obj.toString());
        String[] beanDefNames = context.getBeanDefinitionNames();
        if (beanDefNames == null) return;
        log.info("BeanDefinitionNames: " + beanDefNames.length);
        log.info("=============================================");
        /*for (String beanDefName : beanDefNames) {
            log.info(beanDefName);
        }*/
        log.info("=============================================");
        Object obj1 = context.getBean("user");
        log.info("user: " + obj1);
        Object obj2 = context.getBean("user002");
        log.info("user002: " + obj2);
        Object obj3 = context.getBean(DemoConfig.class);
        log.info("DemoConfig: " + obj3);
        DemoConfig demoConfig = (DemoConfig) obj3;
        log.info("user: " + demoConfig.user());
        log.info("user002: " + demoConfig.user1());
        log.info("org.slf4j.helpers.NOPLogger: " + context.getBean("org.slf4j.helpers.NOPLogger"));
        log.info("pet: " + context.containsBean("pet001"));
        log.info("user003: " + context.containsBean("user003"));
        log.info("zhangsan01: " + context.containsBean("zhangsan01"));
        log.info("Car: " + context.getBean(Car.class));

    }

}
