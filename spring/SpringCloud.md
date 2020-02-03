# SpringCloud

## SpringCloud Eureka

### 基于Netflix Eureka做了二次封装

``` yaml
spring:
  application:
    name: eureka
server:
  port: 8761
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false #是否向注册中心注册自己
    fetch-registry: false #检索注册列表
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  server:
    enable-self-preservation: false
```

### 两个组件组成

- Eureka Server 注册中心

```java
@EnableEurekaServer
```

- Eureka Client 服务注册

```java
@EnableEurekaClient
@EnableDiscoveryClient
```

## 通信

### RPC Dubbo

### HTTP SpringCloud

- RestTemplate
- Fegin

```java
@EnableFeignClients
@FeignClient
```

## Ribbon客户端负载均衡器

### 功能

- 服务发现
- 服务选择规则
- 服务监听

### 主要组件

- ServerList
- ServerListFilter
- IRule


## SpringCloud Config

### 为什么需要统一配置中心

- 不方便维护
- 配置内容安全与权限
- 更新配置项目需重启

### 统一配置中心

