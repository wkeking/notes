## 1.容器中无法输入中文的问题

```shell
# 进入容器 查看字符集
[root@master2 ~]# docker exec -it b18f56aa1e15 /bin/bash
root@b18f56aa1e15:/# locale
LANG=
LANGUAGE=
LC_CTYPE="POSIX"
LC_NUMERIC="POSIX"
LC_TIME="POSIX"
LC_COLLATE="POSIX"
LC_MONETARY="POSIX"
LC_MESSAGES="POSIX"
LC_PAPER="POSIX"
LC_NAME="POSIX"
LC_ADDRESS="POSIX"
LC_TELEPHONE="POSIX"
LC_MEASUREMENT="POSIX"
LC_IDENTIFICATION="POSIX"
LC_ALL=
# 查看容器支持的字符集
root@b18f56aa1e15:/# locale -a
C
C.UTF-8
POSIX
```

**不能输入中文原因：**系统使用的是POSIX字符集，POSIX字符集是不支持中文的，而C.UTF-8是支持中文的 只要把系统中的环境 LANG 改为"C.UTF-8"格式即可解决问题

```shell
docker exec -it b18f56aa1e15 env LANG=C.UTF-8 /bin/bash
```

**注意：**这样修改只是临时修改。要永久修改，需要在创建容器时在dockerfile中设置。
K8s进入pod不能输入中文 也可用此方法解决。