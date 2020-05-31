# oracle2mysql
表结构之间的相互转换

### How to use
在```Application.class```中修改对应的数据库连接信息

设置对应转换器为```true```然后右键运行Main方法即可

最终sql输出到根目录下的```converter.sql```文件夹下

### 转换的信息包括
- 字段名
- 字段类型，相应数据库格式，如果不支持则保留原数据库的类型（如果有不支持的类型，转换过程中不会报错，但是执行出来的sql脚本会报错）
- 是否可为NULL
- 默认值
- 注释
- 主键，Oracle转MySQL，默认MySQL主键自增长

### Oracle and MySQL Data Type Mapper
ORACLE版本信息
```sql
select * from v$version;


Oracle Database 12c Standard Edition Release 12.1.0.2.0 - 64bit Production
```

MySQL版本信息
```sql
SELECT VERSION();

8.0.12
```


ORACLE 和 MySQL 的数据映射关系，目前仅支持常见的以下类型的映射关系

| ORACLE Data Type | MySQL Data Type |
|------------------|-----------------|
| NUMBER(3,0) | TINYINT |
| NUMBER(5,0) | SMALLINT |
| NUMBER(7,0) | MEDIUMINT |
| NUMBER(10,0) | INT, INTEGER|
| NUMBER(20,0) | BIGINT|
| BINARY_DOUBLE, FLOAT(24) | DOUBLE |
| BINARY_FLOAT, FLOAT | FLOAT |
| BLOB, RAW | LONGBLOB |
| CLOB, RAW | LONGTEXT |
| CHAR, NCHAR | CHAR |
| VARCHAR2, NVARCHAR2 | VARCHAR |
| DATE | DATETIME, DATE, TIME |
| TIMESTAMP | TIMESTAMP |


 