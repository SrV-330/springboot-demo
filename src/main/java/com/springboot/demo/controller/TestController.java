package com.springboot.demo.controller;

import com.springboot.demo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private User user;

    @RequestMapping("/user")
    public User test(@RequestParam("name") String uname, HttpServletRequest req) {
        log.info("uri: " + req.getRequestURI());
        Enumeration<String> headerNames = req.getHeaderNames();
        log.info("===================");
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            log.info(name + ": " + req.getHeader(name));
        }
        log.info("===================");
        log.info("name: " + req.getParameter("name"));
        user.setUname(uname);
        return user;
    }
}
