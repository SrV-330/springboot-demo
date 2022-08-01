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
* `@EnableConfigurationProperties`: 启用注解在组件上绑定属性(配置在Config类上)



## 静态资源

* META-INF
* resources
* static
* public

不配置可以直接访问

* `spring.mvc.static-path-pattern`:  配置静态资源映射(默认映射: /**)
* `spring.web.resources.static-locations`: 配置静态资源路径(默认路径: [/META-INF/resources/, /resources/, /static/, /public/])

### favicon

* icon图标:favicon.ico(默认: true)

### 静态资源配置

* `@WebMvcAutoConfiguration`

  ```yaml
  resources: 
  	add-mapping: false # false无法访问静态资源
  	# WebMvcAutoConfiguration.addResourceHandles()方法通过resources.add-mapping属性判断是否禁用静态资源的访问
	cache:
  		period: 11000 # 静态资源缓存时间
  	# WebMvcAutoConfiguration.WelcomePageHandlerMapping()控制首页相关
  ```
  

### rest映射

- HiddenHttpMethodFilter过滤器使用隐藏属性_method将POST变成DELETE,PUT,PATCH
  - mvc.hiddenmethod.filter.enabled:true开启
- @GetMapping
- @PostMapping
- @PutMapping

### dispatchService

- 循环匹配最适合的handleMapping

### 常用注解

- @PathVariable: 路径变量,/user/{id}/{name}
- @RequestHeader: 获取请求头,User-Agent,String,Map<String,String>
- @RequestParam: 获取请求参数,/user?id=1&name=Alice,String,List<String>,Map<String,String>
- @CookieValue: 获取cookie值,String,Cookie
- @RequestBody: 获取请求体,String
- @RequestAttribute: 取出request.setAttribute中的属性

### 如何解析Controller方法上的参数和注解?

#### HandlerAdapter适配器

- dispatcherservlet类中doDispatch调用

- `boolean supports(Object handler);` 寻找符合的handler
- `ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;`调用真正的处理方法
- **继承关系: **`RequestMappingHandlerAdapter` **->** `AbstractHandlerMethodAdapter` **->** `HandlerAdapter`
- **普通请求调用过程: **`handler()` **->** `handleInternal()` **->** `invokeHandlerMethod()` **->** `ServletInvocableHandlerMethod.invokeAndHandle()` **->** `ServletInvocableHandlerMethod.invokeForRequest`()
- `invokeHandlerMethod()`中设置参数解析器`argumentResolvers`解析@RequestParam,@PathVariable等注解

#### RequestMappingHandlerAdapter

- `getDefaultArgumentResolvers()`获取默认的参数处理器
- `getDefaultReturnValueHandlers()`获取默认的返回值处理器

####  HandlerMethodArgumentResolverComposite参数解析器组合

- `public boolean supportsParameter(MethodParameter parameter)`判断是否支持,先从缓存中寻找,再从添加的resolver的List中寻找
- `public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
  			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception`真正处理参数的方法
- XXXResolver还对应目标方法里可以支持的参数,PS:HttpServletRequest

#### HandlerMethodReturnValueHandlerComposite返回值处理器

- 同上

#### ServletInvocableHandlerMethod调用请求方法

- `invokeForRequest()`调用请求方法
  - 先获取目标方法各个参数的信息`getMethodArgumentValues()`
    - getMethodArgumentValues()中两重循环判断各个参数是否支持各个参数解析器
    - 支持则用解析器计算取值

#### Controller方法支持的复杂参数

- **Map,Model (map,model中的数据都放在request的请求域 request.setAttribute()),Errors/BindingResult,RedirectAttributes(重定向携带数据),ServletResponse(response),SessionStatus,UriComponentsBuilder**

- **Map类型参数,会返回mavContainer.getModel(); --> BindingAwareModelMap是Model也是Map**

- **ModelAndView Model:数据,View:目标页面**

#### 自定义参数处理

- **ServletModelAttributeMethodProcessor**