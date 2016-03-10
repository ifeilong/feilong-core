Reduce development, Release ideas (减少开发,释放思想)

            .--.
           /    \
          ## a  a
          (   '._)
           |'-- |
         _.\___/_    __feilong__
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
          |___\_.\_
          `-"--'---'

#Welcome to feilong-core

`核心jar,所有feilong-platform的基础`

# 简介:

1. 在开发中,如果手案上有熟悉使用的工具包,那么开发的速度会如虎添翼,比如如果你熟练使用 `commons-lang`,`commons-collections`,`commons-io`,`commons-beanutils`...etc.等工具包;
1. feilong-core设计的初衷,就是为了提高开发速度,提供一套便捷的开发工具类,目标:`Reduce development, Release ideas`;
1. 基于`commons-lang`,`commons-collections`,`commons-io`,`commons-beanutils`...等工具包,并且基于这些包进行了扩展;

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
com.feilong.core.`bean`  | 封装了`commons-beanutils`,最大的特点是使用了`BeanUtilException`(RuntimeException),常见的类有[BeanUtil](src/main/java/com/feilong/core/bean/BeanUtil.java),[PropertyUtil](src/main/java/com/feilong/core/bean/PropertyUtil.java),[ConvertUtil](src/main/java/com/feilong/core/bean/ConvertUtil.java)
com.feilong.core.`date`  | 提供了常用的日期操作,常见的类有[DateUtil](src/main/java/com/feilong/core/date/DateUtil.java),[CalendarUtil](src/main/java/com/feilong/core/date/CalendarUtil.java),[TimeInterval](src/main/java/com/feilong/core/date/TimeInterval.java),[DateExtensionUtil](src/main/java/com/feilong/core/date/DateExtensionUtil.java)
com.feilong.core.`io`  | 提供了`java.io`包下常用的类的操作
com.feilong.core.`lang`  | 提供了`java.lang`包下常用的类的操作
com.feilong.core.`lang.reflect`  | 提供了常用的`java.lang.reflect`包下类的操作,以及封装了 `org.apache.commons.lang3.reflect`包
com.feilong.core.`net`  | 提供了`java.net`包下常用的类的操作
com.feilong.core.`text`  | 提供了`java.text`包下常用的类的操作
com.feilong.core.`tools.jsonlib`  | 封装了`json-lib-2.4-jdk15`包,提供JSON类型的常见操作,`对象和json的相互转换`,`json数据的format`等等,参见 [JsonUtil](src/main/java/com/feilong/core/tools/jsonlib/JsonUtil.java)
com.feilong.core.`tools.slf4j`  | 调用了`slf4j-api`包下类,来进行字符串的格式化输出,参见 [Slf4jUtil](src/main/java/com/feilong/core/tools/slf4j/Slf4jUtil.java)
com.feilong.core.`util`  | 提供了`java.util`包下常用的类的操作 ,比如[CollectionsUtil](src/main/java/com/feilong/core/util/CollectionsUtil.java),[MapUtil](src/main/java/com/feilong/core/util/MapUtil.java),[PropertiesUtil](src/main/java/com/feilong/core/util/PropertiesUtil.java),[Validator](src/main/java/com/feilong/core/util/Validator.java)...etc.
com.feilong.core.`util.comparator`  | 提供了常用的`Comparator`,最常用的是[PropertyComparator](src/main/java/com/feilong/core/util/comparator/PropertyComparator.java)

# Javadoc:
在此,我们提供在线的Javadoc,以便查阅,参见 [Javadoc](http://venusdrogon.github.io/feilong-platform/javadocs/1.5.0-SNAPSHOT/feilong-core/) 

# 类和方法介绍: 

## com.feilong.core.date 包,时间日期操作核心类:

* date 包是feilong重要的组成部分,提供了常见的日期操作,包含以下五个class

Class | Description 
:---- | :---------
[DateUtil](src/main/java/com/feilong/core/date/DateUtil.java)  | 封装了常见的Date操作
[CalendarUtil](src/main/java/com/feilong/core/date/CalendarUtil.java)  | 封装了常见的Calendar操作
[DateExtensionUtil](src/main/java/com/feilong/core/date/DateExtensionUtil.java)  | 日期扩展工具类,重在个性化输出结果,针对业务个性化显示
[DatePattern](src/main/java/com/feilong/core/date/DatePattern.java)  | 定义了常见的时间Pattern
[TimeInterval](src/main/java/com/feilong/core/date/TimeInterval.java)  | 定义了常见的时间间隔

* [DatePattern](src/main/java/com/feilong/core/date/DatePattern.java)





* [TimeInterval](src/main/java/com/feilong/core/date/TimeInterval.java)

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


#说明

1. 基于`Apache2` 协议,您可以下载代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. 基于`maven3.2`构建;
1. `1.5.0`及以上版本需要`jdk1.7`及以上环境(`1.5.0`以下版本需要`jdk1.6`及以上环境);


# 其他说明
`feilong-core` (V 1.2.0) module 进行了调整:
* 内部package name 从 `com.feilong.commons.core.xxx`,修改成 `com.feilong.core.xxx`
* 其他参见 [release log](https://github.com/venusdrogon/feilong-core/releases)


# About

如果您对feilong core 有任何建议和批评,可以使用下面的联系方式：

* iteye博客:http://feitianbenyue.iteye.com/