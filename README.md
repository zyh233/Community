##Coder社区

###实现的功能:
* github账户授权登录
* 提问功能(使用富文本editor.md)编辑以及HTML展示(可上传图片)
* 问题评论、浏览功能(Mybatis + H2 database)
* 二级评论功能
* 问题文章标签功能
* 添加文章(ElasticSearch)
* 按关键字查找文章功能

###建表语句
```sql
create table USER
(
	ID INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_E5E88873_D6C2_45C6_91F2_C8D61C182FFF) auto_increment,
	ACCOUNT_ID VARCHAR(60),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);
```

###flyway 插件，用于管理更改数据库
```xml
            <plugin>
                <groupId>org.flywaydb</groupId>
                <artifactId>flyway-maven-plugin</artifactId>
                <version>5.2.4</version>
                <configuration>
                    <url>jdbc:h2:D:/project/community</url>
                    <user>param</user>
                    <password>param</password>
                </configuration>
            </plugin>
```
``
mvn flyway:migrate
``

### mybatis.generator 方便生成实体对象以及查询语句
```xml
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <dependencies>
                    <dependency>
                        <groupId>com.h2database</groupId>
                        <artifactId>h2</artifactId>
                        <version>1.4.199</version>
                    </dependency>
                </dependencies>
            </plugin>
```
``
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
``

###快捷键 IDEA
* 一键格式化代碼： Ctrl+Alt+L 
* 自动清除无用导入：ctrl+alt+o
* Ctrl+E 最近打开的文件
* 选中我即将抽取的代码，按快捷键Ctrl + Alt + M 即可
* idea 修改某一变量及其引用 选中变量 shift+f6(shift+fn+f6),


