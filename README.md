# EasyWork

#### 介绍
生成一些模板代码，减少重复工作

#### 软件架构
easywork-comment-parser：解析SQL注释描述

easywork-common-dependency：抽象公共依赖

easywork-generator：生成器代码

easywork-source-parser：使用Java tools.jar实现的Java源文件解析器

easywork-sql：使用shardingsphere-sql-parser实现的SQL解析器

easywork-template-engin：使用FreeMarker实现的模板引擎

easywork-util：工具类


#### 实现功能
[v] 根据建表SQL生成entity、Mapper(interface、Xml)、controller、service

[v] 根据vo和entity生成parse和toEntity方法

[.] 变量名生成工具：使用百度翻译、Google翻译、有道翻译

[ ] 根据JSON生成JavaBean对象

[ ] 生成随机对象初始化代码（用于测试时生成随机对象）


