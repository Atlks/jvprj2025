
-------idx
=MapListHandler
=MapHandler

=calarHandler<
=ColumnListHandler
=KeyedHandler<K>   
=PropertiesHandler



=MapListHandler

* MapListHandler 是 Apache Commons DbUtils 库中的一个处理器类，主要用于将 SQL 查询结果 (ResultSet) 转换为 List<Map<String, Object>> 的形式，其中每个 Map 代表结果集的一行，键为列名，值为列对应的值。
    * MapListHandler 的主要功能是简化 ResultSet 的处理，将其转换为更易于操作的 Java 集合结构。
    *



=MapHandler

作用：将结果集的第一行数据转换为 Map<String, Object>。
使用场景：希望以键值对形式处理单行结果。
示例：
java
Copy
Edit
Map<String, Object> row = runner.query(conn, sql, new MapHandler());
System.out.println(row);




=Scalar<ScalarHandler
作用：提取结果集的单个值（第一行的第一列）。
使用场景：查询单个值，例如计数、总和等。
示例：
java
Copy
Edit
Long count = runner.query(conn, "SELECT COUNT(*) FROM my_table", new ScalarHandler<>());
System.out.println("Count: " + count);



=ColumnListHandler<T>

作用：提取结果集中某一列的所有值，并返回 List<T>。
使用场景：只需要特定列的所有值。
示例：
java
Copy
Edit
List<String> names = runner.query(conn, sql, new ColumnListHandler<>("name"));
System.out.println(names);


=KeyedHandler<K>   
=PropertiesHandler

作用：以某列的值作为键，将结果集每行的数据存储为 Map<K, Map<String, Object>>。
使用场景：需要按特定列分组并保留其他列数据。
示例：
java
Copy
Edit
Map<Integer, Map<String, Object>> grouped = runner.query(conn, sql, new KeyedHandler<>("id"));
System.out.println(grouped);

=PropertiesHandler

作用：将结果集的第一行转换为 Properties 对象。
使用场景：需要将结果以键值对的形式加载到配置中。
示例：
java
Copy
Edit
Properties props = runner.query(conn, sql, new PropertiesHandler());
System.out.println(props.getProperty("key"));



处理器	返回值类型	作用
ArrayHandler	Object[]	返回结果集的第一行，按数组形式存储。
ArrayListHandler	List<Object[]>	返回结果集的所有行，每行是一个数组。
BeanHandler	T（JavaBean）	返回结果集的第一行，映射为 JavaBean。
BeanListHandler	List<T>（JavaBean 列表）	返回结果集的所有行，映射为 JavaBean 列表。
MapHandler	Map<String, Object>	返回结果集的第一行，存储为键值对。
MapListHandler	List<Map<String, Object>>	返回结果集的所有行，存储为键值对列表。
ScalarHandler	T（单个值）	返回结果集的单个值。
ColumnListHandler	List<T>	提取某列的所有值。
KeyedHandler	Map<K, Map<String, Object>>	按列值分组并存储每行数据。
PropertiesHandler	Properties	将第一行转换为 Properties 对象。