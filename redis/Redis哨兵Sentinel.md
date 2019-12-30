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
sentinel monitor <master-name> <ip> <redis-port> <quorum> //quorum：判断为客观下线的支持数量
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

通过分析主服务器返回的INFO命令回复，Sentinel可以获取关于主服务器本身的信息和主服务器属下所有从服务器的信息，Sentinel无需用户提供从服务器的地址信息就可以自动发现从服务器。

## 获取从服务器信息

Sentinel根据主服务器返回的信息，发现主服务器属下的从服务器地址，存储从服务器信息后会创建连接到从服务器的命令连接和订阅连接。

在创建连接之后，Sentinel在默认的情况下，会以十秒一次的频率通过命令连接向从服务器发送INFO命令。

## Sentinel发布订阅

### 订阅

当Sentinel与一个主服务器或者从服务器建立起订阅连接之后，就会通过订阅连接，向服务器发送以下命令：

``` redis
SUBSCRIBE __sentinel__:hello
```

Sentinel对__ sentinel __:hello频道的订阅会一直持续到Sentinel与服务器的连接断开为止。

### 发布

在默认情况下，Sentinel会以每两秒一次的频率，通过命令连接向所有被监视的主服务器和从服务器发送以下格式的指令：

``` redis
PUBLISH __sentinel__:hello "<s_ip>,<s_port>,<s_runid>,<s_epoch>,<m_name>,<m_ip>,<m_port>,<m_epoch>"
```

也就是说，对于每个与Sentinel连接的服务器，Sentinel即通过命令连接向服务器的__ sentinel __ :hello频道发送信息，又通过订阅连接从服务器的__ sentinel __:hello频道接收消息。对于监视同一个服务器的多个Sentinel来说，一个Sentinel发送的信息会被其他Sentinel接收到。

因为一个Sentinel可以通过分析接收到的频道信息来获知其他Sentinel的存在，并通过发送频道信息来让其他Sentinel知道自己的存在，所以用户在使用Sentinel的时候并不需要提供各个Sentinel的地址信息，监视同一个主服务器的多个Sentinel可以自动发现对方。

当Sentinel通过频道信息发现一个新的Sentinel时，会创建一个连向新Sentinel的命令连接。

## 检查主观下线状态

在默认情况下，Sentinel会以每秒一次的频率向所有与他创建了命令连接的实例(主服务器、从服务器、其他Sentinel在内)发送PING命令，并通过实例返回的PING命令回复来判断实例是否在线。

Sentinel配置文件中的**down-after-milliseconds**选项指定了Sentinel判断实例进入主观下线所需的时间，如果一个实例在down-after-milliseconds毫秒内，连续向Sentinel返回无效回复，那么这个实例将被标识为主观下线状态。

## 检查客观下线状态

当Sentinel将一个主服务器判断为主观下线状态之后，为了确认这个主服务器是否真的下线了，它会向同样监视这一主服务的其他Sentinel进行询问，看他们是否也认为主服务器已经进入了下线状态(主观下线或者客观下线)。

当Sentinel从其他Sentinel那里接收到足够数量的已下线判断之后(quorum)，Sentinel就会将服务器判定为客观下线，并对主服务器执行故障转移操作。

## 选举领头Sentinel

当一个主服务器被判断为客观下线时，监视这个下线主服务器的各个Sentinel会进行协商，选举出一个领头Sentinel，并由领头Sentinel对下线主服务器执行故障转移操作。

## 故障转移

在选举产生出领头Sentinel之后，领头Sentinel将对已下线主服务器执行故障转移操作：

1. 在已下线主服务器下的所有从服务器里面，挑选出一个从服务器，并将其转换为主服务器；
2. 让已下线主服务器属下的所有从服务器改为复制新的主服务器；
3. 将已下线主服务器设置为新的主服务器的从服务器，当这个旧的主服务器重新上线时，他就会成为新的主服务器的从服务器。