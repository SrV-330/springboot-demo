package com.springboot.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "prop.car")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    public String cid;
    public String cname;
}
