##同态加密社区


##建表语句
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
###flyway mvn flyway:migrate

###
一键格式化代碼： Ctrl+Alt+L
自动清除无用导入：ctrl+alt+o
Ctrl+E 最近打开的文件
Ctrl+Shift+N
选中我即将抽取的代码，按快捷键Ctrl + Alt + M 即可
idea 修改某一变量及其引用 选中变量 shift+f6(shift+fn+f6),

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
