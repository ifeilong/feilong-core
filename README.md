feilong core 让Java开发更简便的工具包
================

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![build](https://img.shields.io/jenkins/s/https/jenkins.qa.ubuntu.com/precise-desktop-amd64_default.svg "build") 
![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg "JDK 1.7")
[![jar size 107K](https://img.shields.io/badge/size-107K-green.svg "size 107K")](https://github.com/venusdrogon/feilong-platform/tree/repository/com/feilong/platform/feilong-core/1.9.3)
[![javadoc 82%](http://progressed.io/bar/82?title=javadoc "javadoc 82%")](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/) 
[![tests 1416](https://img.shields.io/badge/tests-1416%20%2F%201416-green.svg "tests 1416")](https://github.com/venusdrogon/feilong-core/tree/master/src/test/java/com/feilong/core) 

> Reduce development, Release ideas (减少开发,释放思想)

`核心jar,所有feilong platform的基础` 

# 简介:

1. 目标:`Reduce development, Release ideas` `(减少开发,释放思想)`;
1. 让你从大量重复的底层代码中脱身,提高工作效率;
1. 让你的代码更简炼，易写、易读、易于维护;

# 使用`feilong-core`的理由:

- [使用`feilong-core`的理由](https://github.com/venusdrogon/feilong-core/wiki/Reasons-for-use-feilong-core) 

1.  有常用的工具类 (如 处理日期的 `DateUtil`,处理 集合 的 `CollectionsUtil` 等)
1.	有常用的JAVA常量类 (如日期格式 `DatePattern`, 时间间隔 `TimeInterval` 等)
1.	不必要的`Exception` 转成了`RuntimeException`,减少不必要的代码
1.  国内`中文注释`最完善的API [![javadoc 82%](http://progressed.io/bar/82?title=javadoc "javadoc 82%")](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/) 
1.  有完善的单元测试 [![tests 1416](https://img.shields.io/badge/tests-1416%20%2F%201416-green.svg "tests 1416")](https://github.com/venusdrogon/feilong-core/tree/master/src/test/java/com/feilong/core) 

# 一图概述:

![one-feilong-core](http://venusdrogon.github.io/feilong-platform/mysource/one-feilong-core.png) 


# :dragon: Maven使用配置

feilong-core jar你可以直接在 [仓库](https://github.com/venusdrogon/feilong-platform/tree/repository/com/feilong/platform/feilong-core "仓库") 浏览 

如果你使用 `maven`, 您可以通过以下方式来配置 `pom.xml`:

```XML

	<project>
	
		....
		<properties>
			<version.feilong-platform>1.9.3</version.feilong-platform>
			....
		</properties>
		
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
				<version>${version.feilong-platform}</version>
			</dependency>
			....
		</dependencies>
		....
	</project>
```

# 帮助:

- [Javadoc](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/) 
- [Release notes](http://venusdrogon.github.io/feilong-platform/releasenotes/feilong-core/) 
- [wiki](https://github.com/venusdrogon/feilong-core/wiki) 
- [Site](http://venusdrogon.github.io/feilong-platform/site/feilong-core/) 


# :memo: 说明

1. 基于 [Apache2](https://www.apache.org/licenses/LICENSE-2.0) 协议,您可以下载代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. [require-jdk-version](https://github.com/venusdrogon/feilong-core/wiki/require-jdk-version)
1. [dependencies](https://github.com/venusdrogon/feilong-core/wiki/dependencies)

# Q&A

## Q1:这是重复造轮子吗?

A: https://github.com/venusdrogon/feilong-core/wiki/Repeat-the-wheel

# :cyclone: feilong 即时交流

微信公众号 `feilongjava`							|QQ 群 `243306798`
:---- 										|:---------
 ![](http://i.imgur.com/hM83Xv9.jpg)		|![](http://i.imgur.com/cIfglCa.png)

# :panda_face: About

如果您对本项目有任何建议和批评,可以使用下面的联系方式：

* iteye博客:http://feitianbenyue.iteye.com/