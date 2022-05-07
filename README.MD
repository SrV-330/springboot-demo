# Springboot Demo

> 官方文档2.6.3: https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/
>
> 起步: https://docs.spring.io/spring-boot/docs/current/reference/html/using.html

## 依赖

``` xml
<parent>
    <!--父依赖-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.4</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

## 自动配置

* 默认扫描主程序`*Application`所在的包及其下面的子包所有组件

* `@ComponentScan`,`@ComponentScans`: 指定扫描包
* `@Component`: 使用类名作为ID注册
* `@Bean`: 注解在配置类的方法上,以方法名为ID注册Bean
* `@Controller`,`@Service`,`@Repository`

* spring-boot-autoconfigure: 自动配置包
* `@Configuration`: 指定一个类为配置类,配置类也是一个Component
  * proxyBeanMethods: 默认true.
    * true: 对配置类做代理,从容器中获取的是单实例
    * false: 不是单例,每次获取都是新的实例
* `@Import`: 注解在组件上,用于导入其他组件,以全限定类名作为ID
* `@ImportResource`: 导入XML配置装配
* `@Conditional`: 满足条件进行装配
  * `@ConditionalOnBean`,`@ConditionalOnMissingBean`
* `@ConfigurationProperties`: 注解在组件上绑定属性



## 静态资源

* META-INF

* resources

* static

不配置可以直接访问

* `spring.mvc.static-path-pattern`:  配置静态资源映射(默认映射: /**)
* `spring.web.resources.static-locations`: 配置静态资源路径(默认路径: [/META-INF/resources/, /resources/, /static/, /public/])





