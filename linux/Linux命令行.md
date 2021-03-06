# Linux命令行

## 什么是Linux

Linux可分为四个部分：

- Linux内核
- GNU工具
- 图形化桌面环境
- 应用软件

### Linux内核

Linux系统的核心是内核。内核控制着计算机系统上所有硬件和软件，在必要时分配硬件，并根据需要执行软件。

内核主要负责四中功能：

- 系统内存管理
- 软件程序管理
- 硬件设备管理
- 文件系统管理

#### 系统内存管理

内核不仅管理服务器上的可用物理内存，还可以创建和管理虚拟内存（即实际上不存在的内存）。

内核通过硬盘上的存储空间来实现虚拟内存，这块区域成为交换空间（swap space）。内核不断的在交换空间和实际物理内存之间反复交换虚拟内存中的内容。这使得系统以为它拥有比物理内存更多的可用内存。

#### 软件程序管理

Linux操作系统将运行中的程序成为进程。内核控制着Linux系统如何管理运行在系统上的所有进程。

内核创建了第一个进程（称为init进程）来启动系统上所有其他进程。当内核启动时，它会将init进程加载到虚拟内存中。内核在启动任何其他进程时，都会在虚拟内存中给新进程分配一块专有区域来存储该进程用到的数据和代码。

#### 硬件设备管理

任何Linux系统需要与之通信的设备，都需要在内核代码中加入其驱动程序代码。驱动程序代码相当于应用程序和硬件设备的中间人，允许内核与设备之间交换数据。

Linux系统将硬件设备当成特殊的文件，称为设备文件。

Linux为系统上的每个设备都创建一种称为节点的特殊文件。与设备的所有通信都通过设备节点完成。每个节点都有唯一的数值对供Linux内核识别它。数值对包括一个主设备号和一个次设备号。类似的设备被划分到同样的主设备号下。次设备号用于标识主设备组下的某个特定设备。

#### 文件系统管理

Linux内核支持通过不同类型的 文件系统从硬盘中读写数据。除了自有的诸多文件系统外，Linux还支持从其他操作系统采用的文件系统中读写数据。内核必须在编译时就加入对所有可能用到的文件系统的支持。

Linux内核采用虚拟文件系统（Virtual File System，VFS）作为和每个文件系统交互的接口。这为Linux内核同任何类型文件系统通信提供了一个标准接口。当每个文件系统都被挂载和使用时，VFS将信息都缓存在内存中。

### GNU工具

除了由内核控制硬件设备外，操作系统还需要工具来执行一些标准功能。

GNU组织开发了一套完整的开源的Unix工具。

将Linux内核和GNU操作系统工具整合起来，就产生了一款完整的、功能丰富的免费操作系统。

####核心GNU工具

GNU项目的主旨在于为Unix系统管理员设计出一套类似于Unix的环境。这个目标促使该项目抑制了很多常见的Unix系统命令行工具。供Linux系统使用的这组核心工具被称为coreutils（core utilities）软件包。

GNU coreutils软件包由三部分构成：

- 用以处理文件的工具
- 用以操作文件的工具
- 用以管理进程的工具

#### shell

GNU/Linux shell是一种特殊的交互式工具。它为用户提供了启动程序、管理文件系统中的文件以及运行在Linux系统上的进程的途径。shell的核心是命令行提示符。命令行提示符是shell负责交互的部分。它允许你输入文本命令，然后解释命令，并在内核中执行。

shell包含了一组内部命令，用这些命令可以完成诸如复制文件、移动文件、重命名文件、显示和终止系统中正运行的程序等操作。shell也允许你在命令行提示符中输入程序的名称，它会将程序名传递给内核以启动它。

可以将多个shell命令放入文件中作为程序执行。这些文件被称作shell脚本。你在命令行上执行的任何命令都可放进一个shell脚本中作为一组命令执行。

## Linux文件系统

在Windows中，PC上安装的物理驱动器决定了文件的路径名。Windows会为每个物理驱动器分配一个盘符，每个驱动器都会有自己的目录结构，以便访问存储其中的文件。

Linux则采用了一种不同的方式。Linux将文件存储在单个目录结构中，这个目录被称为虚拟目录（virtual directory）。虚拟目录将安装在PC上的所有存储设备的文件路径纳入单个目录结构中。

Linux虚拟目录结构只包含一个称为根（root）目录的基础目录。根目录下的目录和文件会按照访问他们的目录路径一一列出。

Linux虚拟目录中比较复杂的部分是它如何协调管理各个存储设备。在Linux PC上安装的第一块硬盘称为根驱动器。根驱动器包含了虚拟目录的核心，其他目录都是从那里开始构建。

Linux会在根驱动器上创建一些特别的目录，我们称之为挂载点。挂载点虚拟目录中用于分配额外存储设备的目录。虚拟目录会让文件和目录出现在这些挂载点目录中，然而实际上他们却存储在另外一个驱动器中。

|   目录   |              用途               |
| :----: | :---------------------------: |
|   /    |     虚拟目录的根目录。通常不会在这里存储文件      |
|  /bin  |      二进制目录，存放许多用户级的GNU工具      |
| /boot  |          启动目录，存放启动文件          |
|  /dev  |      设备目录，Linux在这里创建设备节点      |
|  /etc  |           系统配置文件目录            |
| /home  |      主目录，Linux在这里创建用户目录       |
|  /lib  |       库目录，存放系统和应用程序的库文件       |
| /media |      媒体目录，可移动媒体设备的常用挂载点       |
|  /mnt  |     挂载目录，另一个可移动媒体设备的常用挂载点     |
|  /opt  |     可选目录，常用于存放第三方软件包和数据文件     |
| /proc  |     进程目录，存放现有硬件及当前进程的相关信息     |
| /root  |          root用户的主目录           |
| /sbin  |     系统二进制目录，存放许多GNU管理员级工具     |
|  /run  |      运行目录，存放系统运作时的运行时数据       |
|  /srv  |       服务目录，存放本地服务的相关文件        |
|  /sys  |      系统目录，存放系统硬件信息的相关文件       |
|  /tmp  |    临时目录，可以在该目录中创建和删除临时工作文件    |
|  /usr  | 用户二进制文件，大量用户级GNU工具和数据文件都存储在这里 |
|  /var  |    可变目录，用以存放经常变化的文件，比如日志文件    |

## 基本的bash shell命令

### 文件和目录列表

#### 基本列表功能（ls）

显示当前目录下的文件和目录：

``` shell
ls
```

-F参数的ls命令区分文件和目录，在目录名后加了正斜线（/），在可执行文件后面加星号（*）：

```shell
ls -F
```

-a参数可以把隐藏文件和普通文件一起显示出来：

```shell
ls -a
```

-R参数叫做递归选项，它列出当前目录下包含的子目录中的文件：

```shell
ls -R
```

-i参数列出文件的inode编号（文件或目录的inode编号是一个用于标识的唯一数字，这个数字由内核分配给文件系统中的每一个对象）：

```shell
ls -i
```

#### 显示长列表

-l参数会产生长列表格式的输出，包含了目录中每个文件的更多相关信息：

```shell
ls -l
```

这种长列表格式的输出在每一行中列出了单个文件和目录。除了文件名，输出中还有其他有用信息。输出的第一行显示了在目录中包含的总块数。在此之后，每一行都包含了关于文件的下述信息：

- 文件类型，比如目录（d）、文件（-）、字符型文件（c）或块设备（b）；
- 文件的权限；
- 文件的硬链接总数；
- 文件属主的用户名；
- 文件属组的组名；
- 文件的大小（以字节为单位）；
- 文件的上次修改时间（要想查看文件的访问时间，加入另外一个参数：--time=atime）；
- 文件名或者目录名。

#### 过滤输出列表

ls命令支持在命令行中定义过滤器。他会用过滤器来决定应该在输出中显示哪些文件或目录。

```shell
ls -l my_script
```

ls命令能识别标准通配符，并在过滤器中用他们进行模式匹配：

- 问号（？）代表一个字符；
- 星号（*）代表零个或多个字符；
- 中括号（[]）代表在特定位置上可能出现的字符。

```shell
[ai]:可能出现a或者i
[a-i]:可能出现a到i范围内的字符
[!a]:将a排除在外
```

### 处理文件

#### 创建文件（touch）

创建空文件，并将你的用户名作为文件的属主：

```shell
touch filename
```

touch命令还可以用来改变文件的修改时间。

如果只想改变文件的访问时间，可用-a参数：

```shell
touch -a filename
```

#### 复制文件（cp）

在文件系统中将一个文件或目录从一个位置复制到另一个位置：

```shell
cp source destination
```

当source和destination参数都是文件名时，cp命令将源文件复制成一个新文件，并且以destination命名。新文件就像全新的文件一样，有新的修改时间。

-i选项作用是如果目标文件已存在，强制shell询问是否需要覆盖已有文件：

```shell
cp -i source destination
```

-R参数可以在一条命令中递归的复制整个目录中的内容：

```shell
cp -R source destination
```

#### 链接文件（ln）

如需要在系统上维护同一文件的两份或多份副本，除了保存多份单独的物理文件副本之外，还可以采用保存一份物理文件和多个虚拟副本文件的方法。这种虚拟的副本就称为链接。链接是目录中指向文件真实位置的占位符。

在Linux中有两种不同类型的文件链接：

- 符号链接

符号链接是一个实实在在的文件，它指向存放在虚拟目录结构中某个地方的另一个文件。这两个通过符号连接在一起的文件，彼此的内容并不相同。

```shell
ln -s data s_data
```

可以查看文件的inode编号，证明两个文件是完全不同的文件。可以将符号链接看做源文件的引用。

- 硬链接

硬链接会创建独立的虚拟文件，其中包含了原始文件的信息及位置。但是他们从根本上而言是同一个文件，inode编号一致。引用硬链接文件等同于引用了源文件。

```shell
ln data h_data
```

**备注：只能对处于同一存储媒体的文件创建硬链接。要想在不同存储媒体的文件之间创建连接，只能使用符号链接。**

#### 重命名文件（mv）

将文件和目录移动到另一个位置或重命名：

```shell
mv fall fzll
```

-i选项作用是如果目标文件已存在，强制shell询问是否需要覆盖已有文件：

```shell
mv -i fall fzll
```

#### 删除文件（rm）

```shell
rm file
```

-i命令参数提示你是不是要真的删除该文件：

```shell
rm -i file
```

-f参数强制删除：

```shell
rm -f file
```

### 处理目录

#### 创建目录（mkdir）

```shell
mkdir dir
```

-p参数可以同时创建多个目录和子目录：

```shell
mkdir -p a/b/c b
```

#### 删除目录（rmdir）

删除一个空目录：

```shell
rmdir dir
```

也可以在整个非空目录上使用rm命令。使用-r选项使得命令可以向下进入目录，删除其中的文件，然后再删除目录本身：

```shell
rm -r dir
```

### 查看文件内容

#### 查看文件类型（file）

file命令能够探测文件的内部，并决定文件是什么类型的：

```shell
file my_file
```

file不仅能确定文件中包含的文本信息，还能确定文本文件的字符编码。

#### 查看整个文件（cat、more、less）

1. cat

cat命令可以显示文本文件中所有数据：

```shell
cat file
```

-n参数会给所有的行加上行号：

```shell
cat -n file
```

-b参数只给有文本的行加上行号：

```shell
cat -b file
```

-T参数不让制表符出现，会用^I字符组合替换文本中的所有制表符：

```shell
cat -T file
```

2. more

more命令显示文本文件的内容，但会在显示每页数据之后停下来：

```shell
more file
```

more命令是分页工具。可以通过按空格键或回车键以逐行向前的方式浏览文本文件，按q退出。

3. less

less实为more命令的升级版：

```shell
less file
```

less命令能够识别上下键以及上下翻页键。

#### 查看部分文件（tail、head）

1. tail

tail命令默认情况下会显示文本末尾10行：

```shell
tail file
```

-n参数修改所显示的行数（显示2行）：

```shell
tail -n 2 file
```

-f参数会使tail命令保持活动状态，并不断显示添加到文件中的内容：

```shell
tail -f file
```

2. head

head命令默认情况下会显示文本头10行：

```shell
head file
```

-n参数修改所显示的行数（显示2行）：

```shell
head -n 2 file
```

## 更多的bash shell命令

### 检测程序

#### 探查进程（ps）

Linux系统中使用的GNU ps命令支持3种不同类型的命令行参数：

- Unix风格的参数，前面加单破折线；
- BSD风格的参数，前面不加破折线；
- GNU风格的长参数，前面加双破折线。

**Unix风格的参数**

|      参数       |                描述                |
| :-----------: | :------------------------------: |
|      -A       |              显示所有进程              |
|      -N       |          显示于指定参数不符的所有进程          |
|      -a       |       显示除控制进程和无终端进程外的所有进程        |
|      -d       |          显示除控制进程外所有的进程           |
|      -e       |              显示所有进程              |
| -C *cmdlist*  |       显示包含在*cmdlist*列表种的进程       |
| -G *grplist*  |      显示组ID在*grplist*列表种的进程       |
| -U *userlist* |    显示属主的用户ID在*userlist*列表中的进程    |
| -g *grplist*  |     显示会话或组ID在*grplist*列表中的进程     |
| -p *pidlist*  |      显示PID在*pidlist*列表中的进程       |
| -s *sesslist* |      显示会话ID在sesslist列表中的进程       |
| -t *ttylist*  |       显示终端ID在ttylist列表中的进程       |
| -u *userlist* |     显示有效用户ID在userlist列表中的进程      |
|      -F       |       显示更多额外输出（相对于-f参数而言）        |
|  -O *format*  |     显示默认的输出列以及format列表指定的特定列     |
|      -M       |            显示进程的安全信息             |
|      -c       |           显示进程的额外调度器信息           |
|      -f       |            显示完整格式的输出             |
|      -j       |              显示任务信息              |
|      -l       |              显示长列表               |
|  -o *format*  |          仅显示format指定的列           |
|      -y       | 不要显示进程标记（process flag，表明进程状态的标记） |
|      -z       |             显示安全标签信息             |
|      -H       |      用层级格式来显示进程（树状，用来显示父进程）      |
| -n *namelist* |          定义了WCHAN列显示的值           |
|      -w       |          采用宽输出模式，不限宽度显示          |
|      -L       |             显示进程中的线程             |
|      -V       |            显示ps命令的版本号            |

```shell
ps -ef
```

这些扩展的列包含了有用的信息：

- UID：启动这些进程的用户
- PID：进程的进程ID
- PPID：父进程的进程号（如果该进程是由另一个进程启动的）
- C：进程生命周期中的CPU利用率
- STIME：进程启动时的系统时间
- TTY：进程启动时的终端设备
- TIME：运行进程需要的累计CPU时间
- CMD：启动的程序名称

```shell
ps -l
```

使用-l多处的列：

- F：内核分配给进程的系统标记
- S：进程的状态（O代表正在运行；S代表在休眠；R代表可运行，正等待运行；Z代表僵化，进程已结束但父进程已不存在；T代表停止）
- PRI：进程的优先级（越大的数字代表越低的优先级）
- NI：谦让度值用来参与决定优先级
- ADDR：进程的内存地址
- SZ：加入进程被换出，所需交换空间的大致大小
- WCHAN：进程休眠的内核函数的地址

#### 实时监测进程（top）

输出的第一部分显示的时系统的概况：

第一行显示了当前时间、系统的运行时间、登陆的用户数以及系统的平均负载（平均负载由3个值：最近1m的，最近5m的和最近15m的）。

第二行显示了进程概要信息，有多少个进程处在运行、休眠、停止或是僵化状态（僵化状态是进程完成了，但父进程没有响应）。

第三行显示了CPU的概要信息。top根据进程的属主（用户还是系统）和进程的状态（运行、空闲还是等待）将CPU利用率分成几类输出。

紧跟其后的两行说明了系统内存的状态。第一行说的是系统的物理内存：总共由多少内存，当前用了多少，还有多少空闲。后一行说的是同样的信息，不过是针对系统交换空间的状态而言的。

最后一部分显示当前运行中的进程的详细列表。

- PID：进程的ID
- USER：进程属主的名字
- PR：进程的优先级
- NI：进程的谦让度值
- VIRT：进程占用的虚拟内存总量
- RES：进程占用的物理内存总量
- SHR：进程和其他进程共享的内存总量
- S：进程的状态（D代表中断的休眠状态，R代表在运行状态，S代表休眠状态，T代表跟踪状态或停止状态，Z代表僵化状态）
- %CPU：进程使用的CPU时间比例
- %MEM；进程使用的内存占可用内存的比例
- TIME+：自进程启动到目前位置的CPU时间总量
- COMMAND：进程所对应的命令行名称，也就是启动的程序名

在top命令运行时键入交互式命令可改变top的行为：键入f允许选择对输出进行排序的字段，键入d允许修改轮询间隔，键入q可以退出top。

#### 结束进程（kill，killall）

在Linux中，进程之间通过**信号**来通信。进程的信号就是预定义好的一个消息，进程能识别它并决定忽略还是做出反应。

|  信号  |  名称  |        描述        |
| :--: | :--: | :--------------: |
|  1   | HUP  |        挂起        |
|  2   | INT  |        中断        |
|  3   | QUIT |       结束运行       |
|  9   | KILL |      无条件终止       |
|  11  | SEGV |       段错误        |
|  15  | TERM |      尽可能终止       |
|  17  | STOP |   无条件停止运行，但不终止   |
|  18  | TSTP |  停止或暂停，但继续在后台运行  |
|  19  | CONT | 在STOP或TSTP之后恢复执行 |

1.kill命令

kill命令可通过进程ID（PID）给进程发信号。默认情况下，kill命令会向命令行中列出的全部PID发送一个TERM信号。要发送进程信号，你必须是进程的属主或登陆为root用户。

```shell
kill pid
kill -15 pid
```

2.killall命令

killall命令支持通过进程名而不是PID来结束进程。killall命令也支持通配符。

```shell
killall http*
```

### 监测磁盘空间

#### 挂载存储媒体（mount，umount）

Linux文件系统将所有的磁盘都并入一个虚拟目录下，在使用新的存储媒体之前，需要把它放到虚拟目录下。这项工作成为挂载。

1.mount命令

Linux上用来挂载媒体的命令叫做mount。默认情况下，mount命令会输出当前系统上挂载的设备列表。

```shell
mount
```

mount命令提供如下四部分信息：

- 媒体的设备文件名
- 媒体挂载到虚拟目录的挂载点
- 文件系统类型
- 已挂载媒体的访问状态

手动在虚拟目录中挂载设备，需要以root用户身份登陆，或是以root用户身份运行sudo命令。基本命令：

```shell
mount -t type device directory 
```

媒体设备挂载到了虚拟目录后，root用户就有了对该设备的所有访问权限，而其他用户的访问会被限制。

|     参数     |                  描述                   |
| :--------: | :-----------------------------------: |
|     -a     |       挂载/etc/fstab文件中指定的所有文件系统        |
|     -f     |        使mount命令模拟挂载设备，但并不真的挂载         |
|     -F     |        和-a参数一起使用时，会同时挂载所有文件系统         |
|     -v     |           详细模式，将会说明挂载设备的每一步           |
|     -I     | 不启用任何/sbin/mount.filesystem下的文件系统帮助文件 |
|     -l     |     给ext2、ext3或XFS文件系统自动添加文件系统标签      |
|     -n     |      挂载设备，但不注册到/etc/mtab已挂载设备文件中      |
|  -p *num*  |       进行加密挂在时，从文件描述符num中获得密码短语        |
|     -s     |            忽略该文件系统不支持的挂载选项            |
|     -r     |               将设备挂载为只读的               |
|     -w     |           将设备挂载为可读写的（默认参数）            |
| -L *label* |           将设备按指定的*label*挂载            |
| -U *uuid*  |            将设备按指定的*uuid*挂载            |
|     -O     |     和-a参数一起使用，限制命令只作用到特定的一组文件系统上      |
|     -o     |             给文件系统添加特定的选项              |

-o参数允许在挂载文件系统时添加一些以逗号分隔的额外选项：

- ro：以只读形式挂载
- rw：以读写形式挂载
- user：允许普通用户挂载文件系统
- check=none：挂载文件系统时不进行完整性校验
- loop：挂载一个文件

2.umount命令

从Linux系统上移除一个可移动设备时，不能直接从系统上移除，而应该先卸载。

```shell
umount [directory | device]
```

#### 使用df命令

df命令可以很方便的查看多有已挂载磁盘的使用情况。

```shell
df
```

命令输出格式如下：

- 设备的设备文件位置
- 能容纳多少个1024字节大小的块
- 已用了多少个1024字节大小的块
- 还有多少个1024字节大小的块可用
- 已用空间所占的比例
- 设备挂载到了哪个挂载点上

-h把输出中的磁盘空间按照用户易读的形式显示：

```shell
df -h
```

####使用du命令

du命令可以显示某个特定目录（默认情况下时当前目录）的磁盘使用情况。默认情况下，du命令会显示当前目录下所有的文件、目录和子目录的磁盘使用情况，它会以磁盘块为单位来表明每个文件或目录占用了多大存储空间。

```shell
du
```

-c参数可以显示所有已列出文件总的大小：

```shell
du -c
```

-h参数按用户易读的格式输出大小，即用K替代千字节，用M替代兆字节，用G替代吉字节：

```shell
du -h
```

-s参数显示每个输出参数的总计：

```shell
du -s
```

### 处理数据文件

#### 排序数据（sort）

默认情况下，sort命令按照会话指定的默认语言的排序规则对文本文件中的数据行排序：

```shell
sort file
```

-n参数告诉sort命令把数字四倍成数字而不是字符，并且按值排序：

```shell
sort -n file
```

#### 搜索数据（grep）

grep命令会在输入或指定的文件中查找包含匹配指定模式的字符的行：

```shell
grep [options] pattern [file]
```

-v参数输出不匹配该模式的行：

```shell
grep -v t file
```

-n参数显示匹配模式的行所在的行号：

```shell
grep -n t file
```

-c参数输出有多少行含有匹配的模式：

```shell
grep -c t file
```

-e参数指定多个匹配模式：

```shell
grep -e t -e f file
```

#### 压缩数据（gzip）

Linux文件压缩工具：

|    工具    | 文件扩展名 |
| :------: | :---: |
|  bzip2   | .bz2  |
| compress |  .Z   |
|   gzip   |  .gz  |
|   zip    | .zip  |

gzip软件包是GNU项目的产物。这个软件包含下面的工具：

- gzip：用来压缩文件
- gzcat：用来查看压缩过的文本文件内容
- gunzip：用来解压文件

gzip命令会压缩在命令行指定的文件：

```shell
gzip myfile
```

#### 归档数据（tar）

tar命令最开始是用来将文件写到磁带设备上归档的，然而它也能把输出写到文件里，这种用法在Linux上已经普遍用来归档数据了。

```shell
tar function [options] object1 object2 ...
```

function参数定义了tar命令应该做什么：

|  功能  |         长名称          |                 描述                  |
| :--: | :------------------: | :---------------------------------: |
|  -A  |    --concatenate     |     将一个已有tar归档文件追加到另一个已有tar归档文件     |
|  -c  |       -create        |            创建一个新的tar归档文件            |
|  -d  | --diff<br />--delete | 检查归档文件和文件系统的不同之处<br />从已有tar归档文件中删除 |
|  -r  |       --append       |          追加文件到已有tar归档文件末尾           |
|  -t  |        --list        |           列出已有tar归档文件的内容            |
|  -u  |       --update       |  将比tar归档文件中已有的同名文件新的文件追加到该tar归档文件中  |
|  -x  |      --extract       |           从已有tar归档文件中提取文件           |

每个功能可用选项来针对tar归档文件定义一个特定行为：

|   选项    |         描述          |
| :-----: | :-----------------: |
| -C dir  |       切换到指定目录       |
| -f file |   输出结果到文件或设备file    |
|   -j    | 将输出重定向给bzip2命令来压缩内容 |
|   -p    |      保留所有文件权限       |
|   -v    |     在处理文件时显示文件      |
|   -z    | 将输出重定向给gzip命令来压缩内容  |

创建归档文件：

```shell
tar -cvf test.tar test1/ test2/
```

列出tar文件中的内容：

```shell
tar -tf test.tar
```

从tar文件test.tar中提取内容：

```shell
tar -xvf test.tar
```

用gzip压缩过的tar文件可以用tar解压：

```shell
tar -xvzf test.tar.gz
```

## 理解Linux文件权限

### Linux的安全性

Linux安全系统的核心时用户账户。每个能进入Linux系统的用户都会被分配唯一的用户账户。用户对系统中各种对象的访问权限取决于他们登陆系统时用的账户。用户权限时通过创建用户时分配的用户ID（UID）来跟踪的。

#### /etc/passwd文件

Linux系统使用一个专门的文件来将用户的登录名匹配到对应的UID值。这个文件就是/etc/passwd文件，它包含了一些于用户相关的信息。

Linux系统会为各种各样的功能创建不同的用户账户，而这些账户并不是真的用户。这些账户叫做系统账户，是系统上运行的各种服务进程访问资源用的特殊账户。所有运行在后台的服务都需要用一个系统用户账户登陆到Linux系统上。

/etc/passwd文件的字段包含了以下信息：

- 登陆用户名
- 用户密码
- 用户账户的UID（数字形式）
- 用户账户的组ID（GID）（数字形式）
- 用户账户的文本描述（称为备注字段）
- 用户HOME目录的位置
- 用户的默认shell

#### /etc/shadow文件

/etc/passwd文件中的密码字段都被设置成了x，大多数Linux系统都将用户密码保存在另一个单独的文件中/etc/shadow文件。只有特定的程序（比如登陆程序）才能访问这个文件。

/etc/shadow文件的字段包含了以下信息：

- 与/etc/passwd文件中的登陆名字段对应的登录名
- 加密后的密码
- 自上次修改密码后过去的天数密码（自1970年1月1日开始计算）
- 多少天后才能更改密码
- 多少天后必须更改密码
- 密码过期前提前多少天提醒用户更改密码
- 密码过期后多少天禁用用户账户
- 用户账户被禁用的日期（自1970年1月1日到当天的天数表示）
- 预留字段给将来使用

#### 添加新用户（useradd）

用来想Linux系统添加新用户的主要工具是useradd。useradd命令使用系统的默认值以及命令行参数来设置用户账户。系统默认值被设置在/etc/default/useradd文件。

可以使用-D选项的useradd命令查看所有Linux系统中的这些默认值：

```shell
useradd -D
```

- GROUP：新用户会被添加到为GID值的公共组
- HOME：新用户的HOME目录的位置
- INACTIVE：值-1，新用户账户密码在过期后不会被禁用
- EXPIRE：新用户账户设置过期日期
- SHELL：新用户账户的默认shell
- SKEL：值/etc/skel，系统会将目录下的内容复制到用户的HOME目录下
- CREATE_MAIL_SPOOL：系统是否为该用户账户在mail目录下创建一个用于接受邮件的文件

要想在创建用户时改变系统默认值或默认行为，可以使用命令行参数：

|         参数         |                    描述                    |
| :----------------: | :--------------------------------------: |
|    -c *commit*     |                 给新用户添加备注                 |
|   -d *home_dir*    |       为主目录指定一个名字（如果不想用登录名作为主目录名的话）       |
|  -e *expire_date*  |         用YYYY-MM-DD格式指定一个账户过期的日期         |
| -f *inactive_days* | 指定这个账户密码过期后多少天这个账户被禁用；0表示密码一过期就立即禁用，-1表示禁用这个功能 |
| -g *initial_group* |              指定用户登陆组的GID或组名              |
|   -G *group...*    |          指定用户除登陆组之外所属的一个或多个附加组           |
|         -k         |  必须和-m一起使用，将/etc/skel目录的内容复制到用户的HOME目录   |
|         -m         |               创建用户的HOME目录                |
|         -M         |     不创建用户的HOME目录（当默认设置里要求创建时才使用这个选项）     |
|         -n         |             创建一个与用户登陆名同名的新组              |
|         -r         |                  创建系统账户                  |
|    -p *passwd*     |               为用户账户指定默认密码                |
|     -s *shell*     |               指定默认的登陆shell               |
|      -u *uid*      |               为账户指定唯一的UID                |

还可以修改系统的默认值，在-D选项后跟上一个指定的值来修改系统默认的新用户设置：

|          参数          |           描述           |
| :------------------: | :--------------------: |
|  -b *default_home*   |   更改默认的创建用户HOME目录位置    |
| -e *expiration_date* |     更改默认的新账户的过期日期      |
|    -f *inactive*     | 更改默认的新用户从密码过期到账户被禁用的天数 |
|      -g *group*      |      更改默认的组名称或GID      |
|      -s *shell*      |      更改默认的登陆shell      |

```shell
useradd -D -s /bin/tsch
```

#### 删除用户（userdel）

userdel命令会只删除/etc/passwd文件中的用户信息，不会删除系统中属于该账户的任何文件：

```shell
userdel test
```

-r参数，userdel会删除用户的HOME目录以及邮件目录：

```shell
userdel -r test
```

#### 修改用户（usermod，passwd，chpasswd，chage，chfn，chsh）

|    命令    |              描述              |
| :------: | :--------------------------: |
| usermod  | 修改用户账户的字段，还可以指定主要组以及附加组的所属关系 |
|  passwd  |          修改已用用户的密码           |
| chpasswd |      从文件中读取登陆名密码对，并更新密码      |
|  chage   |          修改密码的过期日期           |
|   chfn   |         修改用户账户的备注信息          |
|   chsh   |       修改用户账户的默认登陆shell       |

1.usermod

usermod命令能用来修改/etc/passwd文件中的大部分字段，只需用于想修改的字段对应的命令行参数就可以了，参数大部分跟useradd命令的参数一样（比如，-c修改备注字段，-e修改过期日期，-g修改默认的登陆组）。还有另外一些选项：

|  参数  |      描述      |
| :--: | :----------: |
|  -l  |  修改用户账户的登录名  |
|  -L  | 锁定账户，使用户无法登陆 |
|  -p  |   修改账户的密码    |
|  -U  | 解除锁定，使用户能够登陆 |

2.passwd

passwd命令可以修改用户自己的密码，只有root用户有权限改别人的密码：

```shell
passwd test
```

3.chpasswd

chpasswd命令能从标准输入自动读取登录名和密码对（由冒号分割）列表，然后为用户账户设置。也可以用重定向命令来将含有userid：passwd对的文件重定向给该命令：

```shell
chpasswd < users.txt
```

4.chsh

chsh命令用来快速修改默认的用户登陆shell，使用时必须用shell的全路径作为参数：

```shell
chsh -s /bin/csh test
```

5.chfn

chfn命令提供了在/etc/passwd文件的备注字段中存储信息的标准方法：

```shell
chfn test
```

6.chage

chage命令用来管理用户账户的有效期：

|  参数  |        描述         |
| :--: | :---------------: |
|  -d  |  设置上次修改密码到现在的天数   |
|  -E  |     设置密码过期的日期     |
|  -I  |  设置密码过期的锁定账户的天数   |
|  -m  |  设置修改密码之间最少要多少天   |
|  -W  | 设置密码过期前多久开始出现提醒信息 |

chage命令的日期值可以用下面两种方式的任意一种：

- YYYY-MM-DD格式的日期
- 代表从1970年1月1日起到该日期天数的数值

###Linux组

用户账户在控制单个用户安全性方面很好用，但涉及在共享资源的一组用户时就捉襟见肘。为了解决这个问题，Linux系统采用了另外一个安全概念-组（group）。

每个组都有唯一的GID-跟UID类似，在系统上这是个唯一的数值。除了GID，每个组还有唯一的组名。

#### /etc/group文件

/etc/group文件包含系统上用到的每个组信息：

- 组名
- 组密码
- GID
- 属于该组的用户列表

#### 创建新组（groupadd）

groupadd命令可以在系统上创建新组：

```shell
groupadd test
```

在创建新组时，默认没有用户被分配到该组，可以用usermod命令来弥补这一点。

#### 修改组（groupmod）

groupmod命令可以修改已有组的GID（-g）和组名（-n）

```shell
groupmod -n test1 test2
groupmod -g test1 test2
```

### 文件权限

#### 文件权限符

描述文件和目录权限的编码：

- -代表文件
- d代表目录
- l代表链接
- c代表字符型设备
- b代表块设备
- n代表网络设备
- r代表对象是可读的
- w代表对象是可写的
- x代表对象是可执行的

若没有某种权限，在该权限位会出现单破折线。这3组权限分别对应对象的3个安全级别：

- 对象的属主
- 对象的属组
- 系统其他用户

#### 默认文件权限（amask）

umask命令用来显示和设置这个默认权限：

```shell
umask
umask 0022
```

umask输出第一位是粘着位，是共享文件中用到的概念。

Linux文件权限码：

|  权限  | 二进制值 | 八进制值 |    描述    |
| :--: | :--: | :--: | :------: |
| ---  | 000  |  0   |  没有任何权限  |
| --x  | 001  |  1   |  只有执行权限  |
| -w-  | 010  |  2   |  只有写入权限  |
| -wx  | 011  |  3   | 有写入和执行权限 |
| r--  | 100  |  4   |  只有读取权限  |
| r-x  | 101  |  5   | 有读取和执行权限 |
| rw-  | 110  |  6   | 有读取和写入权限 |
| rwx  | 111  |  7   |  有全部权限   |

umask值只是掩码。它会屏蔽掉不想授予该安全级别的权限，将umask值从对象的全权限值中减掉。对文件来说，全权限的值是666（所有用户都有读和写的权限）；而对目录来说，则是777（所有用户都有读、写、执行权限）。

### 改变安全性设置

#### 改变权限（chmod）

chmod命令用来改变文件和目录的安全性设置：

```shell
chmod options mode file
```

mode参数可以使用八进制模式或符号模式进行安全性设置。

八进制模式设置非常直观，直接用期望赋予问及那的标准3位八进制权限码即可：

```shell
chmod 760 newfile
```

符号模式指定权限的格式

```shell
[ugoa...][+-=][rwxXstugo...]
```

第一组字符定义了权限作用的对象：

- u代表用户
- g代表组
- o代表其他
- a代表上述所有

第二组符号表示是想在现有权限基础上增加权限（+），还是在现有权限基础上移除权限（-），或是将权限设置成后面的值（=）。

第三组符号代表作用到设置上的权限。这个值要比通常的rwx多，有以下几项：

- X：如果对象是目录或者它已有执行权限，赋予执行权限
- s：运行时重新设置UID或GID
- t：保留文件或目录
- u：设置属主权限
- g：设置属组权限
- o：设置其他用户权限

```shell
chmod o+r newfile
```

-R选项可以让权限的改变递归的作用到文件和子目录：

```shell
chmod -R o+r dir
```

#### 改变所属关系（chown，chgrp）

1.chown

chown命令又来改变文件的属主：

```shell
chown options owner[.group] file
```

可用登录名或UID来指定文件的新属主：

```shell
chown test newfile
```

可以同时改变文件的属主和属组：

```shell
chown test.test newfile
```

可以只改变一个目录的默认属组：

```shell
chown .test newfile
```

-R选项配合通配符可以递归的改变子目录和文件的所属关系：

```shell
chown -R test dir
```

-h选项可以改变该文件所有符号链接文件的所属关系：

```shell
chown -h test newfile
```

只有root用户能够改变文件的属主。任何属主都可以改变文件的属组，但前提是属主必须时原属组和目标属组的成员。

2.chgrp

chgrp命令可以更改文件或目录的默认属组：

```shell
chgrp test newfile
```

用户账户必须是文件的属主，还需要是新组的成员。

#### 共享文件

Linux系统上共享文件的方法是创建组。但在一个完整的共享文件的环境中，事情会复杂的多。

有一种简单的方法可以解决这个问题。Linux还为每个文件和目录存储了3个额外的信息位。

- 设置用户ID（SUID）：当文件被用户使用时，程序会以文件属主的权限运行。
- 设置组ID（SGID）：对文件来说，程序会以文件属组的权限运行；对目录来说，目录中创建的新文件会以目录的默认属组作为默认属组。
- 粘着位：进程结束后文件还驻留（粘着）在内存中。

chmod SUID、SGID和粘着位的八进制值：

| 二进制值 | 八进制值 |      描述       |
| :--: | :--: | :-----------: |
| 000  |  0   |    所有位都清零     |
| 001  |  1   |     粘着位置位     |
| 010  |  2   |    SGID位置位    |
| 011  |  3   |  SGID位和粘着位置位  |
| 100  |  4   |    SUID位置位    |
| 101  |  5   |  SUID位和粘着位置位  |
| 110  |  6   | SUID位和SGID位置位 |
| 111  |  7   |     所有位置位     |

umask中四位八进制值第一位就是默认的SUID、SGID和粘着位的默认值。

```shell
chmod g+s testdir
chmod 2644 testdir
```

## 安装软件程序

### 基于Red Hat系统（yum）

#### 列出已安装包

要找出系统上已安装的包：

```shell
yum list installed
```

找出某个特定软件包的详细信息：

```shell
yum list docker
```

查看包时候已安装：

```shell
yum list docker installed
```

找出某个特定文件属于哪个软件包：

```shell
yum provides file_name
```

#### 用yum安装软件

从仓库中安装软件包、所有它需要的库以及依赖的其他包：

```shell
yum install package_name
```

也可以手动下载rpm安装文件并用yum安装，叫做本地安装：

```shell
yum localinstall package_name.rpm
```

#### 用yum更新软件

列出所有已安装包的可用更新：

```shell
yum list updates
```

如果发现某个特定软件包需要更新：

```shell
yum update package_name
```

如果想对更新列表中的所有包进行更新：

```shell
yum update
```

#### 用yum卸载文件

只删除软件包而保留配置文件和数据文件：

```shell
yum remove package_name
```

要删除软件和它所有的文件：

```shell
yum erase package_name
```

#### 处理损坏的包依赖关系

有时在安装多个软件包时，某个包的软件依赖关系可能会被另一个包的安装覆盖掉。这叫做损坏的包依赖关系。

```shell
yum clean all
yum update
```

显示所有包的库依赖关系以及什么软件可以提供这些库依赖关系，一旦知道某个包需要的库，就能安装它们：

```shell
yum deplist xterm
```

--skip-broken选项允许忽略依赖关系损坏的包，继续去更新其他软件包。这可能救不了损坏的包，但至少可以更新系统上的其他包：

```shell
yum update --skip-broken
```

#### yum软件库

yum的仓库定义文件位于/etc/yum.repos.d。需要添加正确的URL，并获得必要的加密密钥。

显示正从哪些仓库中获取软件：

```shell
yum repolist
```

