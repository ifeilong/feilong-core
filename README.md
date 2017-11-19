feilong core 让Java开发更简便的工具包
================

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg "JDK 1.7")
[![jar size 110K](https://img.shields.io/badge/size-110K-green.svg "size 110K")](https://github.com/venusdrogon/feilong-platform/tree/repository/com/feilong/platform/feilong-core/1.10.5)
[![javadoc 83%](http://progressed.io/bar/83?title=javadoc "javadoc 83%")](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/)
[![tests 1561](https://img.shields.io/badge/tests-1561%20%2F%201561-green.svg "tests 1561")](https://github.com/venusdrogon/feilong-core/tree/master/src/test/java/com/feilong/core)
![Coverage 88%](http://progressed.io/bar/85?title=Coverage "Coverage 88%")

> Reduce development, Release ideas (减少开发,释放思想)

focus on J2SE,是 [feilong platform](https://github.com/venusdrogon/feilong-platform) 项目的核心项目

详细的帮助文档 [http://feilong-core.mydoc.io/](http://feilong-core.mydoc.io/)


## 1.简介:

1. 让你从`大量重复`的底层代码中脱身,`提高工作效率`;
1. 让你的代码`更简炼`，`易写`、`易读`、`易于维护`;

## 2.feilong-core 优点:

![](http://i.imgur.com/NCuo13D.png)

**对比1:**

![](http://i.imgur.com/rJnESSq.png)

**对比2:**

![](http://i.imgur.com/FG9ty3Q.png)

- [使用 feilong-core 的理由](https://github.com/venusdrogon/feilong-core/wiki/Reasons-for-use-feilong-core)

1. 有常用的工具类 (如 处理日期的 `DateUtil`,处理 集合 的 `CollectionsUtil` 等)
1. 有常用的JAVA常量类 (如日期格式 `DatePattern`, 时间间隔 `TimeInterval` 等)
1. 不必要的`Exception` 转成了`RuntimeException`,减少不必要的代码
1. 国内`中文注释`最完善的API
1. 有完善的单元测试

## 3.一图概述:

![one-feilong-core](http://venusdrogon.github.io/feilong-platform/mysource/one-feilong-core-1.10.4.png)

这里有详细的帮助文档 http://feilong-core.mydoc.io/

## 4.:dragon: Maven使用配置

`feilong-core` jar你可以直接在 [仓库](https://github.com/venusdrogon/feilong-platform/tree/repository/com/feilong/platform/feilong-core "仓库") 浏览

如果你使用 `maven`, 您可以通过以下方式来配置 `pom.xml`:

```XML
<project>

	....
	<properties>
		<version.feilong-platform>1.10.5</version.feilong-platform>
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

## 5. How to install?

有些小伙伴想下载并 `自行install` 进行研究, 你需要执行以下 `4` 个步骤:

```bat
git clone https://github.com/venusdrogon/feilong-platform.git --depth 1
mvn install -f feilong-platform/pom.xml

git clone https://github.com/venusdrogon/feilong-core.git --depth 1
mvn install -f feilong-core/pom.xml
```

**详细参考** https://github.com/venusdrogon/feilong-core/wiki/install

## 6.帮助:

- [帮助文档](http://feilong-core.mydoc.io/)
- [Javadoc](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/)
- [Release notes](http://venusdrogon.github.io/feilong-platform/releasenotes/feilong-core/)
- [wiki](https://github.com/venusdrogon/feilong-core/wiki)
- [Site](http://venusdrogon.github.io/feilong-platform/site/feilong-core/)

## 7.sonar 扫描

![sonar](http://venusdrogon.github.io/feilong-platform/mysource/sonar/feilong-core.png)

## 8.:memo: 说明

1. 基于 [Apache2](https://www.apache.org/licenses/LICENSE-2.0) 协议,您可以下载代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. [require-jdk-version](https://github.com/venusdrogon/feilong-core/wiki/require-jdk-version)
1. [dependencies](https://github.com/venusdrogon/feilong-core/wiki/dependencies)

## 9.Q&A

### 9.1 Q1:这是重复造轮子吗?

A: https://github.com/venusdrogon/feilong-core/wiki/Repeat-the-wheel

## 10.:cyclone: feilong 即时交流

微信公众号 `feilongjava`							|QQ 群 `243306798`
:---- 										|:---------
 ![](http://i.imgur.com/hM83Xv9.jpg)		|![](http://i.imgur.com/cIfglCa.png)

## 11:panda_face: About

如果您对本项目有任何建议和批评,可以使用下面的联系方式：

* iteye博客:http://feitianbenyue.iteye.com/
