feilong core 让Java开发更简便的工具包
================

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg "JDK 1.7")
[![jar size 110K](https://img.shields.io/badge/size-110K-green.svg "size 110K")](https://github.com/venusdrogon/feilong-platform/tree/repository/com/feilong/platform/feilong-core/1.14.0)
[![javadoc 83%](http://progressed.io/bar/83?title=javadoc "javadoc 83%")](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/)
[![tests 2259](https://img.shields.io/badge/tests-2259%20%2F%202259-green.svg "tests 2259")](https://github.com/venusdrogon/feilong-core/tree/master/src/test/java/com/feilong/core)
![Coverage 91%](http://progressed.io/bar/91?title=Coverage "Coverage 91%")

![sonar](http://venusdrogon.github.io/feilong-platform/mysource/sonar/feilong-core-summary2.jpg)

> Reduce development, Release ideas (减少开发,释放思想)

focus on J2SE,是 [feilong platform](https://github.com/venusdrogon/feilong-platform) 核心项目

详细帮助文档参见 [http://feilong-core.mydoc.io/](http://feilong-core.mydoc.io/)

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

![one-feilong-core](http://venusdrogon.github.io/feilong-platform/mysource/one-feilong-core.png)

这里有详细的帮助文档 http://feilong-core.mydoc.io/

## 4.:dragon: Maven使用配置

`feilong-core` jar你可以直接在 [仓库](https://github.com/venusdrogon/feilong-platform/tree/repository/com/feilong/platform/feilong-core "仓库") 浏览

如果你使用 `maven`, 您可以通过以下方式来配置 `pom.xml`:

```XML
<project>

	....
	<properties>
		<version.feilong-platform>2.1.0</version.feilong-platform>
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

此外强烈建议你使用 feilong 工具类全家桶(含IO操作,Net操作,Json,XML,自定义标签等等工具类)


```XML
<project>

	....
	<properties>
		<version.feilong-platform>2.1.0</version.feilong-platform>
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
			<artifactId>feilong-util-all</artifactId>
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

## 6. Proxying feilong Repositories with Nexus

如果你使用 Sonatype's [Nexus repository manager][2], 你可能想要在Nexus configuration配置中将 feilong repositories 添加到 nexus proxy repositories. 

下面是注意点:

 * 由于 feilong repositories 没有索引. 你需要将 `Remote Repository Access > Download Remote Indexes` 设置为 `false`.
 * 由于 GitHub 对于 `raw` repository url目录不会生成目录列表 , 请将 Nexus `Remote Repository Access > Auto blocking active` 设置为 `false`. 

上面两步操作之后, 你可以看到 `Repository Status` 变成了 `Attempting to Proxy and Remote Unavailable`. 

Nexus 在缺失目录列表的情况下仍然可以访问指定的 artifact, pom 和其他文件 .

示例:

![nexus-config.jpg](http://venusdrogon.github.io/feilong-platform/mysource/nexus-config.jpg)

[1]: http://maven.apache.org/settings.html
[2]: http://nexus.sonatype.org/


## 7.帮助:

- [帮助文档](http://feilong-core.mydoc.io/)
- [Javadoc](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/)
- [Release notes](http://venusdrogon.github.io/feilong-platform/releasenotes/feilong-core/)
- [wiki](https://github.com/venusdrogon/feilong-core/wiki)
- [Site](http://venusdrogon.github.io/feilong-platform/site/feilong-core/)

## 8.sonar 扫描

![sonar](http://venusdrogon.github.io/feilong-platform/mysource/sonar/feilong-core.png)

## 9.:memo: 说明

1. 基于 [Apache2](https://www.apache.org/licenses/LICENSE-2.0) 协议,您可以下载代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. [require-jdk-version](https://github.com/venusdrogon/feilong-core/wiki/require-jdk-version)
1. [dependencies](https://github.com/venusdrogon/feilong-core/wiki/dependencies)

## 10.Q&A

### 10.1 Q1:这是重复造轮子吗?

A: https://github.com/venusdrogon/feilong-core/wiki/Repeat-the-wheel

## 11.:cyclone: feilong 即时交流

|QQ 群 `243306798`
|:---------
|![](http://i.imgur.com/cIfglCa.png)

## 12:panda_face: About

如果您对本项目有任何建议和批评,可以使用下面的联系方式：

* iteye博客:http://feitianbenyue.iteye.com/
