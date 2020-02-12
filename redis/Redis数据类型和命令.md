# Redis数据类型及命令

## Redis数据类型

Redis用到的数据结构有简单动态字符串，链表，字典，跳跃表，整数集合，压缩列表。

**Redis并没有直接使用这些数据结构来实现键值对数据库，而是基于这些数据结构创建了一个对象系统，这个系统包括字符串对象（string object）、列表对象（list object）、哈希对象（hash object）、集合对象（set object）和有序集合（sorted set object）对象这五种类型的对象。**

Redis数据库里面的每个键值对都是由对象组成的，其中：

- **数据库键总是一个字符串对象；**
- 数据库键的值可以是字符串对象、列表对象、哈希对象、集合对象、有序集合对象这五种对象中的其中一种。

## 通用命令

#### keys：获取匹配到的所有key

时间复杂度O(n)

```redis
keys *	//获取所有的key
keys ab?	//获取3个字节，并且前两个是‘ab’的key
```

#### scan：分批匹配

总共的时间复杂度O(n)

``` redis
SCAN cursor [MATCH pattern] [COUNT count]
```

#### dbsize：获取所有key的数量

时间复杂度O(1)

```redis
dbsize	//1
```

#### exists：是否存在指定的key

时间复杂度O(1)

``` redis
exists key
exists a	//返回1
```

#### expire：对key-value设置过期时间，单位秒

时间复杂度O(1)

``` redis
expire key seconds
expire name 60	//key为name的键值对60秒后过期，被删除
```

#### ttl：获取key距离过期还有多少秒

时间复杂度O(1)

``` redis
ttl key
ttl name	//没有过期时间返回-1，没有key返回-2
```

#### persist：取消key的过期时间

时间复杂度O(1)

``` redis
persist key
persist name
```

#### type：返回key对应value的类型

时间复杂度O(1)

``` redis
type key
type abc	//string,list,set,zset,hash,none
```

## 字符串对象命令

字符串最大512M

#### set：设置key-value

时间复杂度O(1)

``` redis
set key value
set name zhangsan
```

#### setnx：key不存在，才设置

时间复杂度O(1)

```redis
setnx key value
setnx name zhangsan
```

#### set xx：key存在，才设置，更新操作

时间复杂度O(1)

```redis
set key value xx
set name zhangsan xx
```

#### get：获取key对应的value

时间复杂度O(1)

``` redis
get key
get name
```

#### del：删除指定的key及value值

时间复杂度O(1)

```redis
del key1 key2 ...
del a b c	//删除key为a，b，c的三个key-value
```

#### incr：key自增1，如果key不存在，自增后get(key)=1

时间复杂度O(1)

``` redis
incr key
incr num
```

#### decr：key自减1，如果key不存在，自减后get(key)=-1

时间复杂度O(1)

``` redis
decr key
decr num
```

#### incrby：key自增指定值k，如果key不存在，自增后get(key)=k

时间复杂度O(1)

``` redis
incrby key value
incrby num 5
```

#### decrby：key自减指定值k，如果key不存在，自减后get(key)=-k

时间复杂度O(1)

```redis
decrby key value
decrby num 5
```

#### mset：设置多个key-value对

时间复杂度O(n)

```redis
mset key1 value1 key2 value2 ...
mset name zhangsan age 18 school beida
```

#### mget：获取多个key对应的value

时间复杂度O(n)

```redis
mget key1 key2 ...
mget name age school
```

#### getset：设置key对应的新值，并返回旧值

时间复杂度O(1)

```redis
getset key value
getset name wangwu
```

#### append：将参数值拼接到旧值的后面

时间复杂度O(1)

```redis
append key value
append school xueyuan
```

#### strlen：返回key对应value的长度(注意中文)

时间复杂度O(1)

```redis
strlen key
strlen name
```

#### incrbyfloat：key自增指定浮点值k，如果key不存在，自增后get(key)=k

时间复杂度O(1)

```redis
incrbyfloat key value
incrbyfloat count 3.5
```

#### getrange：获取字符串指定下标所有的值

时间复杂度O(1)

```redis
getrange key start end
getrange name 0 3
```

#### setrange：设置指定下标所有对应的值

时间复杂度O(1)

```redis
setrange key index value
setrange name 2 w
```

## 哈希对象命令

key对应一个field-value表

#### hget：获取hash key对应field的value

时间复杂度O(1)

```redis
hget key field
hget city qingdao
```

#### hgetall：获取hash key对应的field和value

时间复杂度O(n)

```redis
hgetall key
hgetall city
```

#### hset：设置hash key对应field的value

时间复杂度O(1)

```redis
hset key field value
hset city qingdao shandong
```

#### hsetnx：hash key对应的field不存在，才设置

时间复杂度O(1)

```redis
hsetnx key field value
hsetnx city jinan shandong
```

#### hdel：删除hash key对应field的value

时间复杂度O(1)

```redis
hdel key field
hdel city qingdao
```

#### hexists：判断hash key是否有field

时间复杂度O(1)

```redis
hexists key field
hexists city qingdao
```

#### hlen：获取hash key中field的数量

时间复杂度O(1)

```redis
hlen key
hlen city
```

#### hmget：批量获取hash key中的一批field对应的值

时间复杂度O(n)

```redis
hmget key field1 field2 ...
hmget city qingdao jinan
```

#### hmset：批量设置hash key的一批field-value

时间复杂度O(n)

```redis
hmset key field1 value1 field2 value2 ...
hmset city qingdao shandong jinan shandong
```

#### hvals：返回hash key对应所有field的value

时间复杂度O(n)

```redis
hvals key
hvals city
```

#### hkeys：返回hash key对应所有的field

时间复杂度O(n)

```redis
hkeys key
hkeys city
```

#### hincrby：hash key中field对应值自增指定值

时间复杂度O(1)

```redis
hincrby key field value
hincrby city shanghai 3
```

#### hincrbyfloat：hash key中field对应值自增指定浮点数

时间复杂度O(1)

```redis
hincrbyfloat key field floatvalue
hincrbyfloat city shanghai 3.4
```

## 列表对象命令

有序，可以重复，左右插入弹出列表

#### rpush：从列表右端插入值(1-N个)

时间复杂度O(1-n)

```redis
rpush key value1 value2 ...
rpush class zhangshan lisi
```

#### lpush：从列表左端插入值(1-N个)

时间复杂度O(1-n)

```redis
lpush key value1 value2 ...
lpush class zhangshan lisi
```

#### linsert：从列表指定的值前|后插入newValue

时间复杂度O(n)

```redis
linsert key before|after value newValue
linsert class before wangwu zhaoliu
```

#### lpop：从列表左侧弹出一个元素

时间复杂度O(1)

```redis
lpop key
lpop class
```

#### rpop：从列表右侧弹出一个元素

时间复杂度O(1)

```redis
rpop key
rpop class
```

#### lrem：根据参数count值，从列表中删除所有value相等的元素

1. count>0，从左到右，删除最多count个value相等的元素
2. count<0，从右到左，删除最多Math.abs(count)个value相等的元素
3. count=0，删除所有value相等的元素

时间复杂度O(n)

```redis
lrem key count value
lrem class 0 zhangsan
```

#### ltrim：按照索引范围修剪列表(子列表)

时间复杂度O(n)

```redis
ltrim key start end
ltrim class 1 3
```

#### lrange：获取列表指定索引范围所有元素(包含end)

时间复杂度O(n)

```redis
lrange key start end
lrange class 0 3
```

#### lindex：获取列表指定索引的元素

时间复杂度O(n)

```redis
lindex key index
lindex class 2
```

#### llen：获取列表长度

时间复杂度O(1)

```redis
llen key
llen class
```

#### lset：设置列表指定索引的值

时间复杂度O(n)

```redis
lset key index newValue
lset class 1 zhaoliu
```

#### blpop：lpop阻塞版本，timeout是阻塞超时时间，为0一直阻塞到有元素

时间复杂度O(1)

```redis
blpop key timeout
blpop class 10
```

#### brpop：rpop阻塞版本，timeout是阻塞超时时间，为0一直阻塞到有元素

时间复杂度O(1)

```redis
brpop key timeout
brpop class 0
```

## 集合对象命令

无序，无重复，集合间操作

#### sadd：向集合key添加value，如果value已存在，添加失败

时间复杂度O(1)

```redis
sadd key value
sadd names zhangsan
```

#### srem：将集合key中的value移除

时间复杂度O(n)

```redis
srem key value
srem names zhangsan
```

#### scard：计算集合大小

时间复杂度O(1)

```redis
scard key
scard names
```

#### sismember：判断参数值是否在集合中

时间复杂度O(n)

```redis
sismember key value
sismember names lisi
```

#### srandmember：从集合中随机挑count个元素

时间复杂度O(n)

```redis
srandmember key count
srandmember names 2
```

#### spop：从集合中随机弹出一个元素

时间复杂度O(1)

```redis
spop key
spop names
```

#### smembers：获取集合所有元素

时间复杂度O(n)

```redis
smembers key
smembers names
```

#### sdiff：返回两个集合的差集

```redis
sdiff key1 key2
sdiff user1 user2
```

#### sinter：返回两个集合的交集

```redis
sinter key1 key2
sinter user1 user2
```

#### sunion：返回两个集合的并集

```redis
sunion key1 key2
sunion user1 user2
```

#### sdiff|sinter|sunion + store：将差集|交集|并集添加到新的集合中

```redis
sdiffstore key key1 key2
sinterstore key key1 key2
sunionstore key key1 key2
sdiffstore userdiff user1 user2
```

#### smove：将元素从原始集合移动到目标集合中

```redis
smove key1 key2 value
smove user1 user2 zhangsan
```

## 有序集合对象命令

有序，无重复元素，score+element

#### zadd：添加分值和元素

时间复杂度O(logN)

```redis
zadd key score element ...
zadd student 60 zhangsan
```

#### zrem：删除元素

时间复杂度O(1)

```redis
zrem key element ...
zrem student lisi
```

#### zscore：返回元素的分值

时间复杂度O(1)

```redis
zscore key element
zscore student zhangsan
```

#### zincrby：增加或减少元素的分值

时间复杂度O(1)

```redis
zincrby key increScore element
zincrby student 5 lisi
```

#### zcard：返回元素的总个数

时间复杂度O(1)

```redis
zcard key
zcard student
```

#### zrank：返回元素的排名，从小到大

```redis
zrank key element
zrank student lisi
```

#### zrange：返回指定索引范围的升序元素(分值)

时间复杂度O(logN)+M

```redis
zrange key start end [withscores]
zrange student 0 -1 withscores
```

#### zrangebyscore：返回指定分值范围的升序元素(分值)

时间复杂度O(logN)+M

```redis
zrangebyscore key minScore maxScore [withscores]
zrangebyscore student 2 5 withscores
```

#### zcount：返回有序集合内在指定分数范围内的个数

时间复杂度O(logN)+M

```redis
zcount key minScore maxScore
zcount student 2 5
```

#### zremrangebyrank：删除指定索引内的升序元素

时间复杂度O(logN)+M

```redis
zremrangebyrank key start end
zremrangebyrank student 0 2
```

#### zremrangebyscore：删除指定分数内的升序元素

时间复杂度O(logN)+M

```redis
zremrangebyscore key minScore maxScore
zremrangebyscore student 2 5
```
