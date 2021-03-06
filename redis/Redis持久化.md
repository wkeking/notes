# Redis持久化

Redis持久化机制有两种：RDB快照、AOF日志。

## RDB

RDB持久化功能可以将Redis内存数据结构保存到磁盘中，避免数据意外丢失。

RDB持久化功能所生成的RDB文件是一个经过压缩的二进制文件，通过该文件可以还原生成RDB文件时的内存数据结构。

### RDB文件的创建

Redis有三种生成RDB文件的方式，其中两种命令方式，一种配置自动方式：

- 命令SAVE
- 命令BGSAVE
- 自动间隔性保存

#### 1.SAVE

SAVE命令会阻塞Redis服务器进程，直到RDB文件创建完毕为止，因为Redis是单线程程序，所以在服务器进程阻塞期间，服务器不能处理任何命令请求。

RDB文件IO将严重影响服务器性能，而且阻塞时间随着数据库内存数据的增多而增多。

#### 2.BGSAVE

BGSAVE命令会派生出一个子进程，然后由子进程负责创建RDB文件，服务器进程继续处理命令请求。

Redis 在持久化时会调用glibc的函数fork产生一个子进程，快照持久化完全交给子进程来处理，父进程继续处理客户端请求。子进程刚刚产生时，它和父进程共享内存里面的代码段和数据段。

子进程做数据持久化，它不会修改现有的内存数据结构，它只是对数据结构进行遍历读取，然后序列化写到磁盘中。但是父进程不一样，它必须持续服务客户端请求，然后对内存数据结构进行不间断的修改。

这个时候就会使用操作系统的COW(Copy On Write) 机制来进行数据段页面的分离。数据段是由很多操作系统的页面组合而成，当父进程对其中一个页面的数据进行修改时，会将被共享的页面复制一份分离出来，然后对这个复制的页面进行修改。这时子进程相应的页面是没有变化的，还是进程产生时那一瞬间的数据。

随着父进程修改操作的持续进行，越来越多的共享页面被分离出来，内存就会持续增长。但是也不会超过原有数据内存的 2 倍大小。

#### 3.自动间隔性保存

Redis允许用户通过设置服务器配置的save选项，让服务器每隔一段时间自动执行一次BGSAVE命令。可以通过save选项设置多个保存条件，但只要其中任意一个条件被满足，服务器就会执行BGSAVE命令。

```redis
save 900 1		//服务器在900秒之内，对数据库进行了至少1次修改
save 300 10		//服务器在300秒之内，对数据库进行了至少10次修改
save 60 10000	//服务器在60秒之内，对数据库进行了至少10000次修改
```

### RDB文件的载入

RDB文件的载入工作是在服务器启动时自动执行的，只要Redis服务器在启动时检测到RDB文件存在，并且AOF持久化功能处于关闭状态时，它就会自动载入RDB文件。

服务器在载入RDB文件期间，会一直处于阻塞状态，直到载入工作完成为止。

## AOF

AOF持久化是通过保存Redis服务器所执行的写命令来记录内存数据的。

### AOF持久化的实现

AOF持久化功能的实现可以分为命令追加、文件写入、文件同步三个步骤。

#### 命令追加

当AOF持久化功能处于打开状态时，服务器在执行完一个写命令之后，会以协议格式将被执行的写命令追加到服务器内存的aof_buf缓冲区的末尾。

#### 文件写入

Redis的服务器进程就是一个事件循环，因为服务器在处理文件事件时可能会执行写命令，是的一些内容被追加到aof_buf缓冲区中，所以每次结束一个事件循环之前，都需要将aof_buf缓冲区中的内容写入AOF文件。

#### 文件同步

数据写入文件时，操作系统通常将写入数据暂时保存在一个内存缓冲区中，等到缓冲区的空间被填满或者超过指定时限，才真正的将缓冲区中的数据写入到磁盘里面。

Redis配置提供了**appendfsync**选项从而产生不同的持久化行为：

|   选项   |                             行为                             |
| :------: | :----------------------------------------------------------: |
|  always  |        将aof_buf缓冲区中的所有内容写入并同步到AOF文件        |
| everysec | 将aof_buf缓冲区中的所有内容写入到AOF文件，如果上次同步AOF文件的时间距离现在超过一秒钟，那么对AOF文件进行同步，由专门的线程负责执行 |
|    no    | 将aof_buf缓冲区中的所有内容写入到AOF文件，但并不对AOF文件进行同步，何时同步由操作系统决定 |

### AOF文件的载入

服务器启动时，如果服务器开启了AOF持久化功能，服务器会优先使用AOF文件来还原内存数据。

### AOF重写

因为AOF持久化是通过保存被执行的写命令来记录数据库状态的，所以服务器运行越久，AOF文件越庞大，使用AOF文件进行数据还原所需的时间越多。

Redis提供了AOF文件重写(rewirte)功能，该功能可以创建一个新的AOF文件替换现有的AOF文件，新旧AOF文件保存的数据库状态相同，但新AOF文件不包含冗余命令，体积更小。

**注意，AOF文件重写并不需要对现有AOF文件进行任何读取、分析或者写入操作，这个功能是通过读取服务器当前的数据库状态来实现的。**

Redis提供两种AOF重写的方式：

- 命令BGREWIRTEAOF
- 配置自动重写

#### 1.BGREWIRTEAOF

BGSAVE命令会派生出一个子进程，然后由子进程负责重写AOF文件，服务器进程继续处理命令请求。

子进程中首先从数据库中读取现有的值，然后用一条命令去记录键值对，代替之前记录这个键值对的多条命令。

但是子进程在进行AOF重写期间，服务器进程还继续处理命令请求，从而使得服务器当前数据库状态和重写后的AOF文件所保存的数据库状态不一致。

为了解决这种数据不一致的问题，Redis服务器设置了一个AOF重写缓冲区，这个缓冲区在服务器创建子进程之后开始使用，当Redis服务器执行完一个写命令后，它会同时将这个写命令发送给AOF缓冲区和AOF重写缓冲区。当子进程完成AOF重写工作后，他会向父进程发送一个信号，父进程接收到信号后会将AOF重写缓冲区的所有内容写入到新AOF文件中，并将新AOF文件原子的覆盖旧AOF文件，完成替换。

**在整个AOF重写过程中，只有处理信号任务会对服务器进程造成阻塞。**

#### 2.配置自动重写

配置自动重写过程和原理与执行BGREWIRTEAOF命令相同。只需要在配置文件中设置两个配置：

```redis
auto-aof-rewrite-percentage 100	//AOF重写后增加百分比多少时进行重写
auto-aof-rewrite-min-size 64mb	//AOF文件超过多大时进行重写
```

