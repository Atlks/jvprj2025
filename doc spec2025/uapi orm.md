

一个查询api，可以查询 mysql redis mongodb


sql + sql parser
jpa qry dsl


2. 使用 Spring Data 统一访问
   ✅ Spring Data 提供统一的 Repository 接口，可兼容 MySQL、Redis、MongoDB

java
public interface UserRepository extends CrudRepository<User, String> {}