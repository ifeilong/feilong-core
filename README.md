feilong-platform feilong-core
================

Reduce development, Release ideas

            .--.
           /    \
          ## a  a
          (   '._)
           |'-- |
         _.\___/_   ___feilong___
       ."\> \Y/|<'.  '._.-'
      /  \ \_\/ /  '-' /
      | --'\_/|/ |   _/
      |___.-' |  |`'`
        |     |  |
        |    / './
       /__./` | |
          \   | |
           \  | |
           ;  | |
           /  | |
     jgs  |___\_.\_
          `-"--'---'


#Welcome to feilong-platform feilong-core

`核心jar,所有feilong-platform的基础`

#说明

1. 基于Apache2 协议,您可以下载,代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. 基于maven3.2构建;
1. 需要jdk1.6及以上环境;


# Maven使用配置

```XML
	<project>
		....
		<repositories>
			<repository>
				<id>feilong-repository</id>
				<url>https://raw.github.com/venusdrogon/feilong-platform/repository</url>
			</repository>
		</repositories>
		
		....
		<dependencies>
			....
			<dependency>
				<groupId>com.feilong.platform</groupId>
				<artifactId>feilong-core</artifactId>
				<version>1.2.0</version>
			</dependency>
			....
		</dependencies>
		....
	</project>
```

# feilong-platform module:

Category |Name | Description | JDK编译版本(将来会统一)
----|------------ | ---------|------------
commons |`feilong-core` | 核心jar,所有feilong-platform的基础 | 1.6


# 类和方法介绍: 

## com.feilong.commons.core.date 包,时间日期操作核心类:

* DateUtil时间工具类  


# 项目依赖

```XML
<dependencies>
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-lang3</artifactId>
      <version>3.3.2</version>
      <scope>compile</scope>
    </dependency>
    
    <dependency>
      <groupId>commons-beanutils</groupId>
      <artifactId>commons-beanutils</artifactId>
      <version>1.9.2</version>
      <scope>compile</scope>
    </dependency>
    
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.12</version>
      <scope>compile</scope>
    </dependency>
    
  </dependencies>
```

# About

如果您对feilong platform 有任何建议，可以使用下面的联系方式：

* 新浪微博:http://weibo.com/venusdrogon 
* iteye博客:http://feitianbenyue.iteye.com/