# oracle2mysql
表之间的相互转换

### How to use
在```Application.class```中修改对应的数据库连接信息

设置对应转换器为```true```然后右键运行Main方法即可

### Oracle and MySQL Data Type Mapper
ORACLE 和 MySQL 的版本信息
```
Oracle Version
Oracle Database 12c Standard Edition Release 12.1.0.2.0 - 64bit Production
select * from v$version;
```

ORACLE 和 MySQL 的数据映射关系

| ORACLE Data Type | MySQL Data Type |
|------------------|-----------------|
| binary_double | double |
| binary_float | float |
| blob | longblob ( tinyblob / blob / mediumblob ) |
| clob | longtext ( tinytext / text / mediumtext ) |
| char() | char |
| date | date |
| interval day to second | ? |
| interval year to month | ? |
| long | longblob |
| long raw | ? |
| nclob | ? |
| number | bigint ( tinyint / smallint / mediumint / int / integer / bigint ) |
| nvarchar2() | varchar |
| raw() | ? |
| timestamp | timestamp |
| timestamp with local time zone | ? |
| timestamp with time zone | ? |
| varchar2() | varchar |
 