## 解决k8s创建pod报错No API token found for service account "default", retry after the token is automatically

###前言

在创建pod的时候发现报了这个错误：

```shell
Error from server (ServerTimeout): error when creating "busybox.yaml": No API token found for 
service account "default", retry after the token is automatically created and added to the service 
account
```

###分析

根据报错信息可以初步看出是service account没有设置API token引起的。 

###解决

解决方式有两种：

####方式一：禁用ServiceAccount

编辑/etc/kubenetes/apiserver： 
将以下这行中的ServiceAccount删除即可 

```shell
KUBE_ADMISSION_CONTROL="--admission-control=NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ServiceAccount,ResourceQuota" 
```

改为： 

```shell
KUBE_ADMISSION_CONTROL="--admission-control=NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ResourceQuota"
```

这种方式比较粗暴，可能会遇到必须要用ServiceAccount的情况。

####方式二：配置ServiceAccount

1. 首先生成密钥： 


```shell
openssl genrsa -out /etc/kubernetes/serviceaccount.key 2048
```

2. 编辑/etc/kubenetes/apiserver 

添加以下内容： 

```shell
KUBE_API_ARGS="--service_account_key_file=/etc/kubernetes/serviceaccount.key"
```

3. 再编辑/etc/kubernetes/controller-manager 

添加以下内容： 

```shell
KUBE_CONTROLLER_MANAGER_ARGS="--service_account_private_key_file=/etc/kubernetes/serviceaccount.key"
```

最后无论是哪种解决方式都需要再重启kubernetes服务： 

```shell
systemctl restart etcd kube-apiserver kube-controller-manager kube-scheduler
```

###结语

在这里推荐使用第二种方式，因为在后面配置默认从私有仓库拉取镜像也必须要有ServiceAccount。
