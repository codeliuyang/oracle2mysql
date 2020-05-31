# oracle2mysql
表之间的相互转换

### How to use
在```Application.class```中修改对应的数据库连接信息

设置对应转换器为```true```然后右键运行Main方法即可

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


 