# Redis哨兵Sentinel

Sentinel是Redis高可用性解决方案：由一个或多个Sentinel实例组成的Sentinel系统可以监控任意多个主服务器以及这些主服务器属下的从服务器，并在被监视的主服务器进入下线状态时，自动将下线主服务器属下的某个从服务器升级为新的主服务器，然后由新的主服务器代替已下线的主服务器继续处理命令请求。

## 启动并初始化Sentinel

### 启动命令

``` redis
redis-sentinel /path/sentinel.conf
redis-server /path/sentinel.conf --sentinel
```

### 启动步骤

1. 初始化服务器

Sentinel本质上只是一个运行在特殊模式下的Redis服务器

2. 将普通的Redis服务器使用的代码替换成Sentinel专用代码

PING、SENTINEL、INFO、SUBSCRIBE、UNSUBSCRIBE、PSUBSCRIBE和UNPSUBSCRIBE这七个命令就是客户端可以对Sentinel执行的命令

3. 初始化Sentinel状态
4. 根据给定的配置文件，初始化Sentinel的监视主服务器列表

``` redis
sentinel monitor <master-name> <ip> <redis-port> <quorum>
sentinel down-after-milliseconds <master-name> <milliseconds>
sentinel parallel-syncs <master-name> <numslaves>
sentinel failover-timeout <master-name> <milliseconds>
```

5. 创建联想主服务器的网络连接

对于每个被Sentinel监视的主服务器来说，Sentinel会创建两个连向主服务器的异步网络连接：

一个是命令连接，专门用于向主服务器发送命令，并接受命令回复；

另一个是订阅连接，专门用于订阅主服务器的__ sentinel __:hello频道。

## 获取主服务器信息

Sentinel默认会以每十秒一次的频率，通过命令来连接向被监视的主服务器发送INFO命令，并通过分析INFO命令的回复来获取主服务器的当前信息。

















