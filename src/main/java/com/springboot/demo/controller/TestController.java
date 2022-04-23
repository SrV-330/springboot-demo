package com.springboot.demo.controller;

import com.springboot.demo.bean.User;
import com.springboot.demo.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @Autowired
    @Qualifier("errorException")
    private ErrorException errExcep;

    @RequestMapping("/user")
    public User test(@RequestParam(value = "name", required = false) String uname,
                     @RequestParam(value = "age", required = false) String age,
                     HttpServletRequest req) {
        log.info("uri: " + req.getRequestURI());
        Enumeration<String> headerNames = req.getHeaderNames();
        log.info("===================");
        /*while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            log.info(name + ": " + req.getHeader(name));
        }*/
        log.info("===================");
        log.info("name: " + req.getParameter("name"));
        log.info("age: " + req.getParameter("age"));
        user.setUname(StringUtils.hasLength(uname) ? uname : user.getUname());
        user.setAge(StringUtils.hasLength(age) ? Integer.parseInt(age) : 0);
        return user;
    }

    @ExceptionHandler(Exception.class)
    public ErrorException error(){
        return errExcep;
    }
}
