# Redis配置文件详解

## INCLUDES

Redis只有一个配置文件，如果多个人进行开发维护，那么就需要多个这样的配置文件，这时候多个配置文件就可以在此通过 `include /path/to/local.conf` 配置进来，而原本的 redis.conf 配置文件就作为一个总闸。

``` shell
# include /path/to/local.conf
# include /path/to/other.conf
```

## MODULES

Redis3.0的爆炸功能是新增了集群，而Redis4.0就是在3.0的基础上新增了许多功能，其中这里的自定义模块配置就是其中之一。通过这里的 `loadmodule` 配置将引入自定义模块来新增一些功能。

``` shell
# loadmodule /path/to/my_module.so
# loadmodule /path/to/other_module.so
```

## NETWORK

``` shell
# bind 192.168.1.100 10.0.0.1
# protected-mode yes
# port 6379
# tcp-backlog 511
# timeout 0
# tcp-keepalive 300
```

- bind：绑定redis服务器网卡IP，默认为127.0.0.1,即本地回环地址。这样的话，访问redis服务只能通过本机的客户端连接，而无法通过远程连接。如果bind选项为空的话，那会接受所有来自于可用网络接口的连接。
- protected-mode：Redis3.2版本后新增protected-mode配置，默认是yes，即开启。关闭protected-mode模式，此时外部网络可以直接访问；开启protected-mode保护模式，需配置bind ip或者设置访问密码。
- port：指定Redis运行的端口，默认是6379。
- tcp-backlog：此参数确定了TCP连接中已完成队列(完成三次握手之后)的长度， 当然此值必须不大于Linux系统定义的/proc/sys/net/core/somaxconn值，默认是511，而Linux的默认参数值是128。当系统并发量大并且客户端速度缓慢的时候，可以将这二个参数一起参考设定。
- timeout：设置客户端连接时的超时时间，单位为秒。当客户端在这段时间内没有发出任何指令，那么关闭该连接。默认值为0，表示不关闭。
- tcp-keepalive ：单位是秒，表示将周期性的使用SO_KEEPALIVE检测客户端是否还处于健康状态，避免服务器一直阻塞，官方给出的建议值是300s，如果设置为0，则不会周期性的检测。

## GENERAL

``` shell
# daemonize yes
# pidfile /var/run/redis_6379.pid
# loglevel notice
# logfile "/data/redis/log/6379.log"
# databases 16
```

- daemonize：设置为yes表示指定Redis以守护进程的方式启动（后台启动）。默认值为no
- pidfile：配置PID文件路径，当redis作为守护进程运行的时候，它会把 pid 默认写到 /var/redis/run/redis_6379.pid 文件里面
- loglevel：定义日志级别。默认值为notice，有如下4种取值：

　　　　　　　　　　debug（记录大量日志信息，适用于开发、测试阶段）

　　　　　　　　　　verbose（较多日志信息）

　　　　　　　　　　notice（适量日志信息，使用于生产环境）

　　　　　　　　　　warning（仅有部分重要、关键信息才会被记录）

- logfile：配置log文件地址,默认打印在命令行终端的窗口上
- databases：设置数据库的数目。默认的数据库是DB0 ，可以在每个连接上使用select  <dbid> 命令选择一个不同的数据库，dbid是一个介于0到databases之间的数值。默认值是 16，也就是说默认Redis有16个数据库。

## SNAPSHOTTING

这里的配置主要用来做持久化操作。

``` shell
#save ""  表示停用
#save 900 1  表示900秒内如果至少有1个key的值变化，则保存
#save 300 10  表示300秒内如果至少有10个key的值变化，则保存
#save 60 10000  表示60秒内如果至少有10000个key的值变化，则保存
#stop-writes-on-bgsave-error yes
#rdbcompression yes
#rdbchecksum yes
#dbfilename dump.rdb
#dir /data/redis/
```

- save：这里是用来配置触发 Redis的持久化条件，也就是什么时候将内存中的数据保存到硬盘。当然如果你只是用Redis的缓存功能，不需要持久化，那么你可以注释掉所有的 save 行来停用保存功能。可以直接一个空字符串来实现停用：save ""。
- stop-writes-on-bgsave-error：默认值为yes。当启用了RDB且最后一次后台保存数据失败，Redis是否停止接收数据。这会让用户意识到数据没有正确持久化到磁盘上，否则没有人会注意到灾难（disaster）发生了。如果Redis重启了，那么又可以重新开始接收数据了。
- rdbcompression：默认值是yes。对于存储到磁盘中的快照，可以设置是否进行压缩存储。如果是的话，redis会采用LZF算法进行压缩。如果你不想消耗CPU来进行压缩的话，可以设置为关闭此功能，但是存储在磁盘上的快照会比较大。
- rdbchecksum：默认值是yes。在存储快照后，我们还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能。
- dbfilename：设置快照的文件名，默认是 dump.rdb
- dir：设置快照文件的存放路径，这个配置项一定是个目录，而不能是文件名。使用上面的 dbfilename 作为保存的文件名。

## REPLICATION

``` shell
#replica-serve-stale-data yes
#replica-read-only yes
#repl-diskless-sync no
#repl-diskless-sync-delay 5
#repl-ping-replica-period 10
#repl-timeout 60
#repl-disable-tcp-nodelay no
#repl-backlog-size 1mb
#repl-backlog-ttl 3600
#min-replicas-to-write 3
#min-replicas-max-lag 10
```

- replica-serve-stale-data：默认值为yes。当一个 slave 与 master 失去联系，或者复制正在进行的时候，slave 可能会有两种表现： 如果为 yes ，slave 仍然会应答客户端请求，但返回的数据可能是过时，或者数据可能是空的在第一次同步的时候 ； 如果为 no ，在你执行大部分命令时，slave 都将返回一个 "SYNC with master in progress" 的错误
- replica-read-only：配置Redis的Slave实例是否接受写操作，即Slave是否为只读Redis。默认值为yes。
- repl-diskless-sync：主从数据复制是否使用无硬盘复制功能。默认值为no。
- repl-diskless-sync-delay：当启用无硬盘备份，服务器等待一段时间后才会通过套接字向从站传送RDB文件，这个等待时间是可配置的。这一点很重要，因为一旦传送开始，就不可能再为一个新到达的从站服务。从站则要排队等待下一次RDB传送。因此服务器等待一段时间以期更多的从站到达。延迟时间以秒为单位，默认为5秒。要关掉这一功能，只需将它设置为0秒，传送会立即启动。默认值为5。
- repl-ping-replica-period：定义心跳（PING）间隔。
- repl-timeout：这个参数一定不能小于repl-ping-replica-period，可以考虑为repl-ping-replica-period的3倍或更大。定义多长时间内均PING不通时，判定心跳超时。
- repl-disable-tcp-nodelay：在slave和master同步后（发送psync/sync），后续的同步是否设置成TCP_NODELAY
  假如设置成yes，则redis会合并小的TCP包从而节省带宽，但会增加同步延迟（40ms），造成master与slave数据不一致，假如设置成no，则redis master会立即发送同步数据，没有延迟
- repl-backlog-size：复制积压缓冲区大小
- repl-backlog-ttl：复制积压缓冲区存活时长（所有slaves不可用时，保留repl_backlog多长时间，单位秒）
- min-replicas-to-write：最少存活从节点数量，少于该数量主节点不可写
- min-replicas-max-lag：最大从节点延时时间，大于这个时间并且数量大于min-replicas-to-write则主节点不可写

## SECURITY

``` shell
# requirepass foobared
# rename-command CONFIG ""
```

- requirepass：用于在客户端执行命令前,要求执行 auth password
- rename-command：命令重命名

## CLIENTS

``` shell
# maxclients 10000
```

- maxclients：设置客户端最大并发连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大文件。如果设置 maxclients为0 ，表示不作限制。当客户端连接数到达限制时，Redis会关闭新的连接并向客户端返回max number of clients reached错误信息

## MEMORY MANAGEMENT

``` shell
# maxmemory <bytes>
# maxmemory-policy noeviction
# maxmemory-samples 5
# replica-ignore-maxmemory yes
```

- maxmemory：最大使用内存,超过则触发内存策略
- maxmemory-policy：



1 volatile-lru：从已设置过期时间的数据集中挑选最近最少使用的数据淘汰
2 volatile-lfu：从已设置过期时间的数据集中挑选最近最少访问频率进行淘汰//Redis4.0新增
3 volatile-ttl：从已设置过期时间的数据集中挑选将要过期的数据淘汰
4 volatile-random：从已设置过期时间的数据集中任意选择数据淘汰
5 allkeys-lru：从数据集中挑选最近最少使用的数据淘汰
6 allkeys-lfu：从数据集中选最近最少访问频率进行淘汰//Redis4.0新增
7 allkeys-random：从数据集中任意选择数据淘汰
8 no-enviction：禁止驱逐数据，新写入操作会报错

- maxmemory-samples：lru,lfu会对这个数量的key进行检查,设置过高会消耗cpu,如果小于0则启动失败
- replica-ignore-maxmemory：从节点是否忽略maxmemory配置，默认yes

## LAZY FREEING

``` shell
lazyfree-lazy-eviction no
lazyfree-lazy-expire no
lazyfree-lazy-server-del no
replica-lazy-flush no
```

- lazyfree-lazy-eviction：默认no,那么redis是同步释放内存,也就是停止完成其他请求来做释放内存操作,如果遇到key复杂度很大时(0(n))的会增加请求延时；如果yes,那么则先删除dict中的key,然后把释放内存的任务提交给后台线程做
- lazyfree-lazy-expire：默认no,那么redis是同步删除过期key,也就是停止完成其他请求来做删除过期key,如果遇到key复杂度很大时(0(n))的会增加请求延时；如果yes,把删除key的任务提交给后台线程做
- lazyfree-lazy-server-del：默认no,那么redis是同步删除key,也就是停止完成其他请求来做删除key,如果遇到key复杂度很大时(0(n))的会增加请求延时；如果yes,那么则先删除dict中的key,然后把删除key的任务提交给后台线程做(如果key很小则暂时不删除,只是减少了引用)
- replica-lazy-flush：默认no,那么redis是同步清空数据库,也就是停止完成其他请求来做清空数据库,如果遇到数据库很大会增加请求延时；如果yes,那么则新建dict等数据结构,然后把清空数据库提交给后台线程做

## APPEND ONLY MODE

``` shell
#appendonly yes
#appendfilename "appendonly.aof"
#appendfsync always
#appendfsync everysec
#appendfsync no
#no-appendfsync-on-rewrite no
#auto-aof-rewrite-percentage 100
#auto-aof-rewrite-min-size 64mb
#aof-load-truncated yes
#aof-use-rdb-preamble yes
```

- appendonly：默认redis使用的是rdb方式持久化，这种方式在许多应用中已经足够用了。但是redis如果中途宕机，会导致可能有几分钟的数据丢失，根据save来策略进行持久化，Append Only File是另一种持久化方式，  可以提供更好的持久化特性。Redis会把每次写入的数据在接收后都写入appendonly.aof文件，每次启动时Redis都会先把这个文件的数据读入内存里，先忽略RDB文件。默认值为no。
- appendfilename ：aof文件名，默认是"appendonly.aof"
- appendfsync：aof持久化策略的配置；no表示不执行fsync，由操作系统保证数据同步到磁盘，速度最快；always表示每次写入都执行fsync，以保证数据同步到磁盘；everysec表示每秒执行一次fsync，可能会导致丢失这1s数据
- no-appendfsync-on-rewrite：在aof重写或者写入rdb文件的时候，会执行大量IO，此时对于everysec和always的aof模式来说，执行fsync会造成阻塞过长时间，no-appendfsync-on-rewrite字段设置为默认设置为no。如果对延迟要求很高的应用，这个字段可以设置为yes，否则还是设置为no，这样对持久化特性来说这是更安全的选择。   设置为yes表示rewrite期间对新写操作不fsync,暂时存在内存中,等rewrite完成后再写入，默认为no，建议yes。Linux的默认fsync策略是30秒。可能丢失30秒数据。默认值为no。
- auto-aof-rewrite-percentage：默认值为100。aof自动重写配置，当目前aof文件大小超过上一次重写的aof文件大小的百分之多少进行重写，即当aof文件增长到一定大小的时候，Redis能够调用bgrewriteaof对日志文件进行重写。当前AOF文件大小是上次日志重写得到AOF文件大小的二倍（设置为100）时，自动启动新的日志重写过程。
- auto-aof-rewrite-min-size：64mb。设置允许重写的最小aof文件大小，避免了达到约定百分比但尺寸仍然很小的情况还要重写。
- aof-load-truncated：aof文件可能在尾部是不完整的，当redis启动的时候，aof文件的数据被载入内存。重启可能发生在redis所在的主机操作系统宕机后，尤其在ext4文件系统没有加上data=ordered选项，出现这种现象  redis宕机或者异常终止不会造成尾部不完整现象，可以选择让redis退出，或者导入尽可能多的数据。如果选择的是yes，当截断的aof文件被导入的时候，会自动发布一个log给客户端然后load。如果是no，用户必须手动redis-check-aof修复AOF文件才可以。默认值为 yes。
- aof-use-rdb-preamble：aof前部分用rdb,后面保存时缓存的命令还是用aof格式。

## LUA SCRIPTING

``` shell
lua-time-limit 5000  #一个lua脚本执行的最大时间，单位为ms。默认值为5000.
```

## REDIS CLUSTER

``` shell
cluster-enabled no
cluster-config-file nodes-6379.conf
cluster-node-timeout 15000
cluster-replica-validity-factor 10
cluster-migration-barrier 1
cluster-require-full-coverage yes
cluster-replica-no-failover no
```

- cluster-enabled：集群开关，默认是不开启集群模式。
- cluster-config-file：集群配置文件的名称，每个节点都有一个集群相关的配置文件，持久化保存集群的信息。 这个文件并不需要手动配置，这个配置文件有Redis生成并更新，每个Redis集群节点需要一个单独的配置文件。请确保与实例运行的系统中配置文件名称不冲突。默认配置为nodes-6379.conf。
- cluster-node-timeout ：可以配置值为15000。节点互连超时的阀值，集群节点超时毫秒数。
- cluster-slave-validity-factor ：可以配置值为10。如果从节点和master距离上一次通信超过 (node-timeout * replica-validity-factor) + repl-ping-replica-period时间,则没有资格失效转移为master。
- cluster-migration-barrier ：可以配置值为1。当存在孤立主节点后(没有从节点),其他从节点会迁移作为这个孤立的主节点的从节点(前提是这个从节点之前的主节点至少还有这个数额个从节点)。
- cluster-require-full-coverage：默认情况下，集群全部的slot有节点负责，集群状态才为ok，才能提供服务。  设置为no，可以在slot没有全部分配的时候提供服务。不建议打开该配置，这样会造成分区的时候，小分区的master一直在接受写请求，而造成很长时间数据不一致。

## SLOW LOG

``` shell
slowlog-log-slower-than 10000
slowlog-max-len 128
```

- slowlog-log-slower-than：执行命令大于这个值计入慢日志，如果设置为0,则所有命令全部记录慢日志，单位毫秒。
- slowlog-max-len：最大的慢日志条数,会占用内存。

## LATENCY MONITOR

``` shell
latency-monitor-threshold 0
```

- latency-monitor-threshold：为了收集可能导致延时的数据根源,redis延时监控系统在运行时会采样一些操作通过 LATENCY命令 可以打印一些图样和获取一些报告这个系统仅仅记录那个执行时间大于或等于通过latency-monitor-threshold配置来指定的时间，当设置为0时这个监控系统关闭单位毫秒