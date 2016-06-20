feilong core
================

Reduce development, Release ideas (减少开发,释放思想)

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![build](https://img.shields.io/wercker/ci/wercker/docs.svg "build") 
![doc](https://img.shields.io/badge/docs-78.1%25-green.svg "doc") 
![tests](https://img.shields.io/badge/tests-275%20%2F%20275-green.svg "tests") 


#Welcome to feilong-core

`核心jar,所有feilong-platform的基础`

# 简介:

1. 在开发中,如果手案上有熟悉使用的工具包,那么开发的速度会如虎添翼,比如你熟练使用 `commons-lang`,`commons-collections`,`commons-beanutils`,`guava`...etc.等工具包;
1. `feilong-core`设计的初衷,就是为了提高开发速度,提供一套便捷的开发工具类,目标:`Reduce development, Release ideas`;
1. 可以帮助你的代码更简短精炼，更重要是它增强了代码的可读性
1. 可以简化你的代码，使它易写、易读、易于维护。它能提高你的工作效率，让你从大量重复的底层代码中脱身。
1. 基于`commons-lang`,`commons-collections`,`commons-beanutils`...等工具包,并且基于这些包进行了扩展;


# :dragon: Maven使用配置

```XML

	<project>
	
		....
		<properties>
			<version.feilong-platform>1.7.1</version.feilong-platform>
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

# Javadoc:
在此,我们提供在线的Javadoc,以便查阅,参见 [Javadoc](http://venusdrogon.github.io/feilong-platform/javadocs/feilong-core/) 

# wiki:
更多内容,你可以参见 [WiKi](https://github.com/venusdrogon/feilong-core/wiki) 


# 项目依赖

```XML

	<dependencies>
	
	    <dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>3.4</version>
	    </dependency>
	    
	    <dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-collections4</artifactId>
			<version>4.1</version>
	    </dependency>
	    
	    <dependency>
			<groupId>commons-beanutils</groupId>
			<artifactId>commons-beanutils</artifactId>
			<version>1.9.2</version>
	    </dependency>
	    
	    <dependency>
			<groupId>net.sf.json-lib</groupId>
			<artifactId>json-lib</artifactId>
			<version>2.4</version>
			<classifier>jdk15</classifier>
	    </dependency>
	    
	  </dependencies>
```


# :memo: 说明

1. 基于`Apache2` 协议,您可以下载代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. 基于`maven3.3`构建;
1. [require-jdk-version](https://github.com/venusdrogon/feilong-core/wiki/require-jdk-version)


# :panda_face: About

如果您对feilong core 有任何建议和批评,可以使用下面的联系方式：

* iteye博客:http://feitianbenyue.iteye.com/