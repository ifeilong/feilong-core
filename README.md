#重要说明
`feilong-core` (V 1.2.0) module 进行了调整:
* 内部package name 从 `com.feilong.commons.core.xxx`,修改成 `com.feilong.core.xxx`
* 其他参见 https://github.com/venusdrogon/feilong-core/releases release log

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

# 简介:

1. 在开发中,如果手案上有熟悉使用的工具包,那么开发的速度会如虎添翼,比如如果你熟练使用 `commons-lang`,`commons-collections`,`commons-io`,`commons-beanutils`...etc.等工具包;
1. feilong-core设计的初衷,就是为了提高开发速度,提供一套便捷的开发工具类,目标:`Reduce development, Release ideas`;
1. 基于`commons-lang`,`commons-collections`,`commons-io`,`commons-beanutils`...等工具包,并且基于这些包进行了扩展;

#说明

1. 基于`Apache2` 协议,您可以下载代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. 基于`maven3.2`构建;
1. `1.5.0`及以上版本需要`jdk1.7`及以上环境(`1.5.0`以下版本需要`jdk1.6`及以上环境);


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
				<version>1.4.1</version>
			</dependency>
			....
		</dependencies>
		....
	</project>
```

# feilong-core Package介绍:

Package | Description 
:---- | :---------
com.feilong.core.`bean`  | 封装了`commons-beanutils`,最大的特点是使用了`BeanUtilException`(RuntimeException)
com.feilong.core.`date`  | 提供了常用的日期操作
com.feilong.core.`io`  | 提供了`java.io`包下常用的类的操作
com.feilong.core.`lang`  | 提供了`java.lang`包下常用的类的操作
com.feilong.core.`lang.reflect`  | 提供了常用的`java.lang.reflect`包下类的操作,以及封装了 `org.apache.commons.lang3.reflect`包
com.feilong.core.`net`  | 提供了`java.net`包下常用的类的操作
com.feilong.core.`text`  | 提供了`java.text`包下常用的类的操作
com.feilong.core.`tools.jsonlib`  | 封装了`json-lib-2.4-jdk15`包,提供JSON类型的常见操作,`对象和json的相互转换`,`json数据的format`等等
com.feilong.core.`tools.slf4j`  | 调用了`slf4j-api`包下类,来进行字符串的格式化输出,参见 [Slf4jUtil](https://github.com/venusdrogon/feilong-core/blob/master/src/main/java/com/feilong/core/tools/slf4j/Slf4jUtil.java)
com.feilong.core.`util`  | 提供了`java.util`包下常用的类的操作 ,比如`CollectionsUtil`,`MapUtil`...etc.
com.feilong.core.`util.comparator`  | 提供了常用的`Comparator`,最常用的是`PropertyComparator`


# 类和方法介绍: 

## com.feilong.core.date 包,时间日期操作核心类:

* DateUtil时间工具类  


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
	<groupId>commons-io</groupId>
	<artifactId>commons-io</artifactId>
	<version>2.4</version>
    </dependency>
    
    <dependency>
	<groupId>net.sf.json-lib</groupId>
	<artifactId>json-lib</artifactId>
	<version>2.4</version>
	<classifier>jdk15</classifier>
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
      <optional>true</optional>
    </dependency>
    
  </dependencies>
```

# About

如果您对feilong core 有任何建议和批评,可以使用下面的联系方式：

* 新浪微博:http://weibo.com/venusdrogon
* iteye博客:http://feitianbenyue.iteye.com/