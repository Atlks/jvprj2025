


使用@Embedded 模式更好，会直接将内嵌对象展开在表中，方便索引检索。。

fld如果实现序列化接口，可自动java序列化存储到表中。但不可读
和json转换器，可读了，但虽然可以存储，但不方便单独索引，不需要检索的fld可以使用json序列化


@Entity
@Table(name = "users")
@Data
@EntityListeners(UserEntityListener.class)  // 注册监听器
public class User {

    @Embedded
// @Convert(converter = EmlConverter.class)
public  Eml email;   //如何指定以json格式序列化



内嵌List集合呢，不会重复字段吗在数据表中
在 JPA 中，如果你使用 @Embedded 来嵌入一个 List 集合，它不会直接存储在宿主实体的表中，而是会创建一个 额外的表 来存储集合数据。这是因为 关系数据库不支持直接存储集合类型。
