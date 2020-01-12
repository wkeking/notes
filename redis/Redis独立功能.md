# Redis独立功能

## 发布订阅

### 订阅与退订

``` redis
SUBSCRIBE <channel>		//订阅频道
UNSUBSCRIBE <channel>	//退订频道
PSUBSCRIBE <pattern>	//订阅模式 PSUBSCRIBE name.*
PUNSUBSCRIBE <pattern>	//退订模式
```

### 发布

``` redis
PUBLISH <channel> <message>	//发布命令
```

当一个Redis客户端执行发布命令时，服务器执行以下两个操作：

- 将消息message发送给channel频道的所有订阅者
- 如果一个或多个模式pattern与频道channel相匹配，那么将消息message发送给pattern模式的订阅者

### 查看订阅信息

``` redis
PUBSUB CHANNELS [pattern]
```

命令用于返回服务器当前被订阅的频道，其中pattern参数是可选的：

- 如果不给定pattern参数，那么命令返回服务器当前被订阅的所有频道
- 如果给定pattern参数，那么命令返回服务器当前被订阅的频道中与pattern模式相匹配的频道

``` redis
PUBSUB NUMSUB [channel1 channel2 ...]
```

命令接收任意多个频道作为输入参数，并返回这些频道的订阅者数量。

``` redis
PUBSUB NUMPAT
```

命令用于返回服务器当前被订阅模式的数量。

### 不足

PubSub 的生产者传递过来一个消息，Redis 会直接找到相应的消费者传递过去。如果一个消费者都没有，那么消息直接丢弃。如果开始有三个消费者，一个消费者突然挂掉了，生产者会继续发送消息，另外两个消费者可以持续收到消息。但是挂掉的消费者重新连上的时候，这断连期间生产者发送的消息，对于这个消费者来说就是彻底丢失了。

如果 Redis 停机重启，PubSub 的消息是不会持久化的，毕竟 Redis 宕机就相当于一个消费者都没有，所有的消息直接被丢弃。

## 事务

事务提供了一种将多个命令请求打包，然后一次性、按顺序的执行多个命令的机制，并且在事务执行期间，服务器不会中断事务而改去执行其他客户端的命令请求，它会将事务中的所有命令都执行完毕，然后才去处理其他客户端的请求。

``` redis
MULTI		//开始事务
EXEC		//执行事务
DISCARD		//取消事务
```

MULTI命令的执行标志着事务的开始，将执行命令的客户端表示为事务状态。

当一个客户端处于非事务状态时，这个客户端发送的命令会立即被服务器执行，当一个客户端切换到事务状态时，会将命令放入一个事务队列中，然后向客户端返回QUEUED回复。

当处于事务状态的客户端发送EXEC命令时，服务器遍历事务队列，执行队列中的所有命令，最后将执行命令所得的结果全部返回给客户端。

### 入队错误

事务在入队命令的过程中，出现了命令不存在，或者命令格式不正确的情况，那么Redis将拒绝执行这个事务。

### 执行错误

事务在执行过程中发生了一些不能再入队时被服务器发生的错误，这些命令只能在命令实际执行的时候被触发。即使在事务的执行过程中出现了错误，服务器也不会中断事务的执行，他会继续执行事务中余下的命令，并且已执行的命令不会被出错的命令影响。

所以，Redis事务并不完全满足原子性，仅仅满足事务的隔离性。

### WATCH

``` redis
WATCH key
```

WATCH命令是一个乐观锁，它可以在EXEC命令执行之前，监视任意数量的数据库键，并在EXEC命令执行时，检查被监视的键是否至少有一个已经被修改过了，如果是的话，服务器将拒绝执行事务，并向客户端返回代表事务执行失败的空回复。

## 排序

Redis的SORT命令可以对列表键、集合键或者有序集合键的值进行排序。

``` redis
SORT <key> [BY pattern] [LIMIT start count] [GET pattern] [ASC|DESC] [ALPHA] [STORE dstkey] 
```

### ALPHA选项

使用ALPHA选项，SORT命令可以对包含字符串值的键进行排序。

### ASC选项和DESC选项

在默认情况下，SORT命令执行升序排序（ASC），排序后的结果按值的大小从小到大排列。

使用DESC选项，可以让命令执行降序排序。

### LIMIT选项

使用LIMIT选项，可以让SORT命令只返回其中一部分已排序的元素。

### BY选项

使用BY选项，SORT命令可以指定某些字符串键或者某个哈希键所包含的某些域（表示为键名—>字段名）来作为元素的权重，对一个键进行排序。

``` redis
SADD name zhangsan lisi wangwu
MSET name-zhangsan 2 name-lisi 1 name-wangwu 3
SORT name BY name-*
```

*权重参考键虽然支持散列类型，但是"*"只能在"->"符号前面（ 即键名部分）才有用，在"->"后（ 即域名部分） 会被当成域名本身而不会作为占位符被元素的值替换。*

### GET选项

使用GET选项，可以让SORT命令在对键进行排序之后，根据被排序的元素，以及GET选项所指定的模式，查找并返回某些键的值。

``` redis
SADD students peter jack tom
SORT students ALPHA
1) "jack"
2) "peter"
3) "tom"
MSET peter-name PETER jack-name JACK tom-name TOM
SORT students ALPHA GET *-name
1) "JACK"
2) "PETER"
3) "TOM"
```

### STORE选项

在默认情况下，SORT命令只向客户端返回排序结果，而不保存排序结果。

使用STORE选项，可以将排序结果保存在指定的键里面。

### 多个选项的执行顺序

一个SORT命令请求通常会用到多个选项，而这些选项的执行顺序有先后之分：

- 排序：在这一步，命令会使用ALPHA、ASC或DESC、BY这几个选项，对输入键进行排序，并得到一个排序结果集。
- 限制排序结果集的长度：在这一步，命令会使用LIMIT选项，对排序结果集的长度进行限制，只有LIMIT选项指定的那部分元素会被保留在排序结果集中。
- 获取外部键：在这一步，命令会使用GET选项，根据排序结果集中的元素，以及GET选项指定的模式，查找并获取指定键的值，并用这些值来作为新的排序结果集。
- 保存排序结果集：在这一步，命令会使用STORE选项，将排序结果集保存到指定的键上面去。
- 向客户端返回排序结果集：在最后一步，命令遍历排序结果集，并依次向客户端返回排序结果集中的元素。

### 选项的摆放顺序

调用SORT命令时，出了GET选项之外，改变选项的摆放顺序并不会影响SORT命令执行这些选项的顺序。

## 慢查询日志

Redis的慢查询日志功能用于记录执行时间超过给定时长的命令请求，用户可以通过这个功能产生的日志来监视和优化查询速度。

服务器配置有两个和慢查询日志相关的选项：

``` redis
slowlog-log-slower-than 10000	//指定执行时间超过多少微秒的命令请求会被记录到日志上
slowlog-max-len 128				//指定服务器最多保存多少条慢查询日志
```

- SLOWLOG GET：查看服务器所保存的慢查询日志
- SLOWLOG LEN：查看日志数量
- SLOWLOG RESET：清除所有慢查询日志

## 监视器

``` redis
MONITOR
```

客户端通过执行MONITOR命令，可以将自己编程一个监视器，实时的接收并打印出服务器当前处理的命令请求的相关信息。

## 位图

位图，也成为二进制位数组。位图不是特殊的数据结构，它的内容其实就是普通的字符串，也就是 byte 数组。我们可以使用普通的 get/set 直接获取和设置整个位图的内容，也可以使用位图操作 getbit/setbit 等将 byte 数组看成「位数组」来处理。

### GETBIT命令

GETBIT命令用于返回位数组bitarray在offset偏移量上的二进制位的值。

时间复杂度为O(1)

``` redis
GETBIT <bitarray> <offset>
```

### SETBIT命令

SETBIT命令用于将位数组bitarray在offset偏移量上的二进制位的值设置为value，并向客户端返回二进制位被设置之前的旧值。

时间复杂度为O(1)

``` redis
SETBIT <bitarray> <offset> <value>
```

### BITCOUNT命令

BITCOUNT命令用于统计给定位数组中，值为1的二进制位的数量。

``` redis
BITCOUNT <bitarray> [start end]
```

### BITPOS命令

BITPOS命令用于统计给定位数组中，第一个值为bit的位置。

``` redis
BITPOS <bitarray> <bit> [start end]
```

### BITOP命令

BITOP命令支持对字节执行逻辑与（&）、逻辑或（|）、逻辑异或（^）和逻辑非（~）操作。

``` redis
BITOP <operation> <result> <key> [key ...]
```

- BITOP AND：程序用&操作计算出所有输入的位图的逻辑与结果，并保存在指定的键上面
- BITOP OR：程序用|操作计算出所有输入的位图的逻辑或结果，并保存在指定的键上面
- BITOP XOR：程序用^操作计算出所有输入的位图的逻辑异或结果，并保存在指定的键上面
- BITOP NOT：程序用~操作计算出所有输入的位图的逻辑非结果，并保存在指定的键上面

### BITFIELD命令

Redis的3.2版本新增了BITFIELD命令，该命令可以对指定位图的片段进行读写，但是最多只能处理64个连续的位，如果超过64位，可以使用多次子指令，BITFIELD可以一次执行多个子指令。

BITFIELD有三个子指令，分别是 GET/SET/INCRBY。

``` redis
BITFIELD key [GET type offset] [SET type offset value] [INCRBY type offset increment] [OVERFLOW WRAP|SAT|FAIL]
```

type是由符号位和位数量组成：有符号数 （i） ，无符号数 （u）

所谓有符号数是指获取的位数组中第一个位是符号位，剩下的才是值。如果第一位是 1，那就是负数。无符号数表示非负数，没有符号位，获取的位数组全部都是值。有符号数最多可以获取 64 位，无符号数只能获取 63 位 (因为 Redis 协议中的 integer 是有符号数，最大 64 位，不能传递 64 位无符号值)。如果超出位数限制，Redis 就会告诉你参数错误。 

``` redis
BITFIELD w GET u4 0	//从第一个位开始取 4 个位，结果是无符号数 (u)
BITFIELD w SET u8 8 97	//从第 9 个位开始，将接下来的 8 个位用无符号数 97 替换
BITFIELD w INCRBY u4 2 1	//从第三个位开始，对接下来的 4 位无符号数 +1
```

bitfield 指令提供了溢出策略子指令 overflow，用户可以选择溢出行为，默认是折返 (wrap)，还可以选择失败 (fail) 报错不执行，以及饱和截断 (sat)，超过了范围就停留在最大最小值。overflow 指令只影响接下来的第一条指令，这条指令执行完后溢出策略会变成默认值折返 (wrap)。 

## HyperLogLog

HyperLogLog提供不精确的去重计数方案，虽然不精确但是也不是非常不精确，标准误差是0.81% 。

Redis对HyperLogLog的存储进行了优化，在计数比较小时，它的存储空间采用稀疏矩阵存储，空间占用很小，仅仅在计数慢慢变大，稀疏矩阵占用空间渐渐超过了阈值时才会一次性转变成稠密矩阵，会占用12k的空间。 

### PFADD

将元素添加到HyperLogLog数据结构中

``` redis
PFADD key element [element ...]
```

### PFCOUNT

使用单个键调用时，返回由存储在指定变量中的HyperLogLog数据结构计算的近似基数，如果该变量不存在，则返回0。

使用多个键调用时，通过将存储在所提供的键中的HyperLogLog内部合并到临时HyperLogLog中，返回传递的HyperlogLog的联合的近似基数。

``` redis
PFCOUNT key [key ...]
```

### PFMERGE

将多个HyperLogLog值合并为一个唯一值，该值将近似观察到的源HyperLogLog结构集的联合的基数。 

``` redis
PFMERGE destkey sourcekey [sourcekey ...]
```

## 布隆过滤器

布隆过滤器可以理解为一个不怎么精确的set结构，当你使用它的contains方法判断某个对象是否存在时，它可能会误判。但是布隆过滤器也不是特别不精确，只要参数设置的合理，它的精确度可以控制的相对足够精确，只会有小小的误判概率。 

**当布隆过滤器说某个值存在时，这个值可能不存在；当它说不存在时，那就肯定不存在。**

### 安装使用

https://github.com/RedisLabsModules/redisbloom/

``` shell
wget https://github.com/RedisLabsModules/rebloom/archive/v1.1.1.tar.gz

loadmodule /path/rebloom.so		//redis配置文件增加模块
```

### BF.RESERVE

``` redis
BF.RESERVE <key> <error_rate> <initial_size>
```

- initial_size：表示预计放入的元素数量，当实际数量超出这个数值时，误判率会上升，默认100
- error_rate：误判率范围，默认0.01
- key：如果对应的 key 已经存在，`bf.reserve`会报错

### BF.ADD和BF.MADD

添加单个或多个元素

``` redis
BF.ADD <key> <element>
BF.MADD <key> <element> [element...]
```

### BF.EXISTS

查询单个或多个元素是否存在

``` redis
BF.EXISTS <key> <element>
BF.MEXISTS <key> <element> [element...]
```

## GEO

Redis在3.2版本以后增加了地理位置GEO模块，GEO可以保存元素的经纬度，并可以计算经纬度的距离。

GEO存储结构上使用的是zset，意味着我们可以使用zset相关的指令来操作GEO数据，所以删除指令可以直接使用zrem 指令即可。 

### GEOADD

``` redis
GEOADD key longitude latitude member [longitude latitude member ...]
```

longitude：经度

latitude：维度

### GEODIST

可以用来计算两个元素之间的距离

``` redis
GEODIST key member1 member2 [unit]
```

距离单位可以是m、km、ml、ft，分别代表米、千米、英里和尺。 

### GEOPOS

指令可以获取集合中任意元素的经纬度坐标，可以一次获取多个。 

``` redis
GEOPOS key member [member ...]
```

### GEOHASH

可以获取元素的经纬度编码字符串，它是base32编码。 

<http://geohash.org/> 可以查看GEOHASH编码字符串的位置。

``` redis
GEOHASH key member [member ...]
```

### GEORADIUSBYMEMBER

最为关键的指令，它可以用来查询指定元素附近的其它元素。 

``` 
GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREDIST key]
```

- `WITHDIST`：返回距离。距离以命令的半径参数的单位相同的单位返回。

- `WITHCOORD`：返回匹配项目的经度，纬度坐标。

- `WITHHASH`：还以52位无符号整数的形式返回项目的原始 geohash 编码的有序集合分数。

## 漏斗限流

漏斗限流是最常用的限流方法之一，顾名思义，这个算法的灵感源于漏斗（funnel）的结构。

漏斗的容量是有限的，如果将漏嘴堵住，然后一直往里面灌水，它就会变满，直至再也装不进去。如果将漏嘴放开，水就会往下流，流走一部分之后，就又可以继续往里面灌水。如果漏嘴流水的速率大于灌水的速率，那么漏斗永远都装不满。如果漏嘴流水速率小于灌水的速率，那么一旦漏斗满了，灌水就需要暂停并等待漏斗腾空。 

Redis4.0提供了一个限流Redis模块，它叫redis-cell。该模块也使用了漏斗算法，并提供了原子的限流指令。 

<https://github.com/brandur/redis-cell/> 

``` shell
loadmodule /path/libredis_cell.so	//redis配置文件增加模块
```

``` redis
cl.throttle <key> <capacity> <operations> <seconds> [quota]
```

- capacity：漏斗容量
- operations/seconds：漏斗速率
- quota：可选值，默认1

``` redis
cl.throttle laoqian:reply 15 30 60
1) (integer) 0   # 0 表示允许，1表示拒绝
2) (integer) 15  # 漏斗容量capacity
3) (integer) 14  # 漏斗剩余空间left_quota
4) (integer) -1  # 如果拒绝了，需要多长时间后再试(漏斗有空间了，单位秒)
5) (integer) 2   # 多长时间后，漏斗完全空出来(left_quota==capacity，单位秒)
```