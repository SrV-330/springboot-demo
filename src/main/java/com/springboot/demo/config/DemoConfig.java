package com.springboot.demo.config;

import com.springboot.demo.bean.Pet;
import com.springboot.demo.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Import(org.slf4j.helpers.NOPLogger.class)
@Configuration(proxyBeanMethods = true)
@ImportResource("classpath:beans.xml")
public class DemoConfig {

    @Bean
    public User user() {
        return new User("001", "user001",1);
    }

    @Bean("user002")
    public User user1() {
        return new User("002", "user002",2);
    }

    @Bean("pet001")
    @ConditionalOnBean(name = "user002")
    public Pet pet() {
        return new Pet("001", "pet001");
    }

    @Bean("user003")
    @ConditionalOnMissingBean(name = "user002")
    public User user2() {
        return new User("003", "user003",3);
    }
}
