


可能是fld  是mysql kwd

renm is ok

总结排查步骤
实体类是否标注 @Entity + @Id

是否被 Spring Boot 或 Hibernate 扫描到

hibernate.hbm2ddl.auto 设置是否生效

字段类型是否可识别

控制台是否输出 SQL 和异常

数据库是否支持建表权限，是否大小写敏感