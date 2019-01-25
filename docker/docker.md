# Docker容器



## 一.Docker基础

### 简介

Docker是一个开源的应用容器引擎，可以轻松的位任何应用创建一个轻量级的、可移植的、自给自足的容器。开发者在笔记本上编译测试通过的容器可以批量的在生产环境中部署，包括VMs（虚拟机）、bare metal、OpenStack集群和其他的基础应用平台。

### 容器和虚拟机

![容器](./images/docker-container-appliction.png)

容器是应用层的抽象，它将代码和依赖关系打包在一起。多个容器可以在同一台机器上运行，并与其他容器共享操作系统内核，每个容器在用户空间中作为**独立进程**运行。容器占用的空间比VM少（容器镜像的大小通常位几十MB），可以用更少的VM和操作系统，处理更多的应用程序。

![虚拟机](./images/docker-container-vm.png)

虚拟机（VM）是物理硬件的抽象，将一台服务器转变为多台服务器。虚拟机管理程序允许多台虚拟机在一台计算机上运行。每个VM都包含**操作系统的完整副本、应用程序、必要的二进制文件和库**，占用数十GB。虚拟机启动很慢。

### 为什么使用Docker

- 更高效的利用系统资源

  由于容器不需要进行硬件虚拟以及运行完整操作系统等额外开销，Docker对系统资源的利用率更高。无论是应用执行速度、内存损耗或者文件存储速度，都要比传统虚拟机技术更高效。因此，相比虚拟机技术，一个相同配置的主机，往往可以运行更多数量的应用。

- 更快速的启动时间

  传统的虚拟机技术启动应用服务往往需要数分钟，而Docker容器应用，由于直接运行于宿主内核，无需启动完整的操作系统，因此可以做到秒级。大大节约了开发、测试、部署的时间。

- 一致的运行环境

  开发过程中一个常见的问题是环境一致性问题。由于开发环境、测试环境、生产环境不一致，导致有些bug并未在开发过程中被发现。而Docker的镜像提供了除内核外完整的运行时环境，确保了应用运行环境一致性，从而不会出现【这段代码在我的机器上没问题呀】这类问题。

- 持续交付和部署

  对开发和运维人员来说，最希望的就是一次创建或配置，可以在任意地方正常运行。

  使用Docker可以通过定制应用镜像来实现持续集成、持续交付、部署。开发人员可以通过Dockerfile来进行镜像构建，并结合持续集成（Continuous Integration）系统进行集成测试，而运维人员则可以直接在生产环境中快速部署该镜像，甚至结合持续部署（ContinuousDelivery/Deployment）系统进行自动部署。

  而且使用Dockerfile是镜像构建透明化，不仅仅开发团队可以理解应用运行环境，也方便运维团队理解应用运行所需条件，帮助更好的生产环境部署该镜像。

- 更轻松的迁移

  由于Docker确保了执行环境的一致性，使得应用的迁移更加容易。Docker可以在很多平台上运行，无论是物理机、虚拟机、公有云、私用云，甚至是笔记本，其运行结果是一致的。因此用户可以很轻易的将在一个平台运行的应用，迁移到另一个平台上，而不用担心运行环境的变化导致应用无法正常运行的情况。

- 更轻松的维护和扩展

  Docker使用的分层存储以及镜像技术，使得应用重复部分的复用更为容易，也使得应用的维护更新更加简单，基于基础镜像进一步扩展镜像也变得非常简单。此外，Docker团队同各个开源项目团队一起维护了一大批高质量的官方镜像，既可以直接在生产环境使用，又可以作为基础进一步定制，大大的降低了应用服务的镜像制作成本。

## 二.核心概念

Docker包括三个基本概念：镜像（Image）、容器（Container）、仓库（Repository）。

###Docker镜像

操作系统分为内核和用户空间。对于Linux而言，内核启动后，会挂载root文件系统为其提供用户空间支持。而Docker镜像（Image），就相当于是一个root文件系统。

Docker镜像是一个**特殊的文件系统**，除了提供容器运行时所需的程序、库、资源、配置等文件外，还包含了一些为运行时准备的一些配置参数（如匿名卷、环境变量、用户等）。**镜像不包含任何动态数据，其内容在构建之后也不会被改变**。

- 分层存储

  因为镜像包含操作系统完整的root文件系统，其体积往往是庞大的，因此在Docker设计时，就充分利用Union FS的技术，将其设计为**分层存储的架构**。所以严格来说，镜像并非是一个ISO那样的打包文件，镜像只是一个虚拟的概念，其实际体现并非有一个文件组成，而是由一组文件系统组成，或者说，**由多层文件系统联合组成**。

  镜像构建时，会一层层构建，前一层是后一层的基础。每一层构建完就不会再发生改变，后一层上的任何改变只会发生在自己这一层。比如，删除前一层文件的操作，实际不是真的删除前一层的文件，而是仅在当前层标记为该文件已删除。在最终容器运行的时候，虽然不会看到这个文件，但是实际上该文件会一直跟随镜像。因此，在构建镜像的时候，需要额外小心，每一层尽量只包含该层需要添加的东西，任何额外的东西应该在该层构建结束前清理掉。

  **分层存储的特征还使得镜像的复用、定制变得更为容易。**甚至可以用之前构建好的镜像作为基础层，然后进一步添加新的层，以定制自己所需的内容，构建新的镜像。

### Docker容器

镜像（Image）和容器（Container）的关系，就像面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。

容器的实质时进程，但与直接在宿主执行的进程不同，容器进程运行于属于自己的独立的命名空间。因此容器可以拥有自己的root文件系统、自己的网络配置、自己的进程空间，甚至自己的用户ID空间。容器内的进程是运行在一个隔离的环境里，使用起来，就好像是在一个独立于宿主的系统下操作一样。这种特性使得容器封装的应用比直接在宿主运行更加安全。

镜像使用的是分层存储，容器也是如此。每一个容器运行时，是以镜像为基础层，在其上创建一个当前容器的存储层，可以称这个为容器运行时读写而准备的存储层为**容器存储层**。

**容器存储层的生存周期和容器一样，容器消亡时，容器存储层也随之消亡。因此，任何保存于容器存储层的信息都会随容器的删除而丢失。**

按照 Docker 最佳实践的要求，容器不应该向其存储层内写入任何数据，容器存储层要保持**无状态化**。所有的文件写入操作，都应该使用**数据卷（Volume）、或者绑定宿主目录**，在这些位置的读写会跳过容器存储层，直接对宿主（或网络存储）发生读写，其性能和稳定性更高。

数据卷的生存周期独立于容器，容器消亡，数据卷不会消亡。因此，使用数据卷后，容器删除或者重新运行之后，数据却不会丢失。

### Docker仓库

镜像构建完成后，可以很容易的在当前宿主机上运行，但是，如果需要在其它服务器上使用这个镜像，我们就需要一个集中的存储、分发镜像的服务，Docker Registry 就是这样的服务。

一个 Docker Registry 中可以包含多个仓库（ Repository ）；每个仓库可以包含多个标签（ Tag ）；每个标签对应一个镜像。

通常，一个仓库会包含同一个软件不同版本的镜像，而标签就常用于对应该软件的各个版本。我们可以通过 <仓库名>:<标签> 的格式来指定具体是这个软件哪个版本的镜像。如果不给出标签，将以 latest 作为默认标签。

仓库名经常以 两段式路径 形式出现，比如 jwilder/nginx-proxy ，前者往往意味着 DockerRegistry 多用户环境下的用户名，后者则往往是对应的软件名。但这并非绝对，取决于所使用的具体 Docker Registry 的软件或服务。

- Docker Registry公开服务

  Docker Registry 公开服务是开放给用户使用、允许用户管理镜像的 Registry 服务。一般这类公开服务允许用户免费上传、下载公开的镜像，并可能提供收费服务供用户管理私有镜像。

  最常使用的 Registry 公开服务是官方的**Docker Hub**，这也是默认的 Registry，并拥有大量的高质量的官方镜像。除此以外，还有 CoreOS 的 Quay.io，CoreOS 相关的镜像存储在这里；Google 的 Google Container Registry，Kubernetes 的镜像使用的就是这个服务。

  由于某些原因，在国内访问这些服务可能会比较慢。国内的一些云服务商提供了针对 DockerHub 的镜像服务（ Registry Mirror ），这些镜像服务被称为加速器。常见的有 阿里云加速器、DaoCloud 加速器 等。使用加速器会直接从国内的地址下载 Docker Hub 的镜像，比直接从 Docker Hub 下载速度会提高很多。在 安装 Docker 一节中有详细的配置方法。

  国内也有一些云服务商提供类似于 Docker Hub 的公开服务。比如 时速云镜像仓库、网易云镜像服务、DaoCloud镜像市场、阿里云镜像库等。


## 三.安装Docker引擎

### CentOS环境下安装Docker

Docker目前支持CentOS7及以后的版本。系统要求64位操作系统，内核版本至少为3.10。

首先，为了方便添加软件源，以及支持devicemapper存储类型，安装如下软件包：

```shell
sudo yum update
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

添加Docker稳定版本的yum软件源：

```shell
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

更新yum软件源缓存，并安装Docker：

```shell
sudo yum update
sudo yum install -y docker-ce
```

确认Docker服务启动正常：

```shell
sudo systemctl start docker
```

### 通过脚本安装

用户还可以使用官方提供的shell脚本来在Linux系统上安装Docker的最新正式版本，该脚本会自动检测系统信息并进行相应配置：

```shell
curl -fsSL https://get.docker.com/ | sh
或
wget -qO- https://get.docker.com/ | sh
```

### 配置Docker服务

为了避免每次使用Docker命令时需要切换到特权身份，可以将当前用户加入安装中自动创建的docker用户组：

```shell
sudo usermod -aG docker USER_NAME
```

修改镜像地址：

```shell
/etc/docker/daemon.json
{
  "registry-mirrors": ["https://xxxxx.mirror.aliyuncs.com"]
}
```

对于CentOS系统，服务通过systemd来管理，配置文件路径为（没有则创建）：

```shell
/etc/systemd/system/docker.service.d/docker.conf
```

更新配置后需要通过systemctl命令来管理Docker服务：

```shell
sudo systemctl daemon-reload
sudo systemctl start docker.service
```

查看Docker信息：

```shell
docker info
```

查看Docker服务的日志信息：

```shell
journalctl -u docker.service
```

## 四.使用Docker镜像

### 获取镜像

pull命令直接从Docker Hub镜像源来下载镜像：

```shell
docker [image] pull NAME[:TAG]
```

### 查看镜像信息

使用images命令列出镜像：

```shell
docker images 或 docker image ls
```

列出信息字段：

- 来自哪个仓库
- 镜像的标签信息
- 镜像的ID（唯一标识镜像），如果两个镜像的ID相同，说明他们实际上指向了同一个镜像，只是具有不同标签名称
- 创建时间，说明镜像最后的更新时间
- 镜像大小

使用tag命令来为本地镜像任意添加新的标签：

```shell
docker tag ubuntu:latest myubuntu:test
```

myubuntu:test镜像ID跟ubuntu:latest是完全一致的，他们实际上指向了同一个镜像文件，只是别名不同而已。tag命令添加的标签实际上起到了类似链接的作用。

使用inspect命令查看详细信息，包括制作者、适应架构、各层的数字摘要等：

```shell
docker [image] inspect ubuntu:latest
```

使用history命令查看镜像历史：

```shell
docker history ubuntu:latest
```

### 搜寻镜像

使用search命令可以搜索仓库中的镜像：

```shell
docker search [option] keyword
```

返回包含关键字的镜像，其中包括镜像名字、描述、收藏数（表示该镜像的受欢迎程度）、是否官方创建、是否自动创建等。

### 删除和清理镜像

使用rm命令可以删除镜像：

```shell
docker rmi IMAGE [IMAGE...] 或 docker image rm IMAGE [IMAGE...]
```

- -f，-force：强制删除镜像，即使有容器依赖它

使用Docker一段时间后，系统中可能会遗留一些临时的镜像文件，以及一些没有被使用的镜像，可以使用prune命令进行清理：

```shell
docker image prune
```

- -f，-force：强制删除镜像，而不进行提示确认

### 存出和载入镜像

存出镜像：

```shell
docker [image] save -o ubuntu_18.04.tar ubuntu:18.04
```

之后可以复制ubuntu_18.04.tar文件将该镜像分享。

载入镜像：

```shell
docker [image] load -i ubuntu_18.04.tar
```

### 上传镜像

使用push命令可以上传镜像到仓库：

```shell
docker [image] push NAME[:TAG] | [REGISTRY_HOST[:REGISTRY_PORT]/]NAME[:TAG]
```

## 五.操作Docker容器

### 创建容器

使用create命令新建一个容器：

```shell
docker [container] create ubuntu:latest
```

使用start命令来启动一个容器：

```shell
docker [container] start containerID
```

使用run命令来创建并启动一个容器：

```shell
docker [container] run ubuntu:latest
```

允许用户交互，exit退出：

```shell
docker [container] run -it ubuntu:latest /bin/bash
```

更多的时候，需要让Docker容器在后台以守护态（Daemonized）形式运行。可以通过添加-d参数来实现：

```shell
docker [container] run -d ubuntu:latest
```

某些时候，执行run命令的时候因为命令无法正常执行容器会出错直接退出，此时可以查看退出的错误代码，常见包括：

- 125：Docker daemon执行出错，例如指定了不支持的Docker参数
- 126：所指定命令无法执行，例如权限出错
- 127：容器内命令无法找到

使用logs命令获取容器的输出信息：

```shell
docker [container] logs containerID
```

























## 三.Dockerfile

### 1.使用Dockerfile定制镜像

**镜像的定制实际上就是定制每一层所添加的配置、文件。**如果我们可以把每一层修改、安装、构建、操作的命令都写入一个脚本，用这个脚本来构建、定制镜像，那么无法重复的问题、镜像构建透明性的问题、体积的问题就都会解决。这个脚本就是Dockerfile。

Dockerfile是一个文本文件，期内包含了一条条的指令（Instruction），每一条指令构建一层，因此每一条指令的内容，就是描述该层应当如何构建。

### 2.Dockerfile指令详解

- FROM指定基础镜像

  所谓定制镜像，那一定是以一个镜像为基础，在其上进行定制。而FROM就是指定基础镜像，因此一个Dockerfile中FROM是必备的指令，并且**必须是第一条指令**。

  除了选择现有镜像为基础镜像外，Docker还存在一个图书的镜像，名为**scratch**。这个镜像是虚拟的概念，并不实际存在，它表示一个空白的镜像。

  如果以scratch为基础镜像的话，意味着不以任何镜像为基础，接下来缩写的指令将作为镜像第一层开始存在。

- RUN执行命令

  ​



