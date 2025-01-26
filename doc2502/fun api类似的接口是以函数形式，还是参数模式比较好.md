




    public static SortedMap<String, Object> qrySqlAsMap(String sql, String jdbcUrl){

    }

    public static List<SortedMap<String, Object>> qrySql(String sql, String jdbcUrl) 


=函数比较好，，curring柯里化嘛
r=rest接口也是分开的，不是一个简单的sql查询api。。更加明确
=混合模式，也提供一个大接口

柯里化（Currying） 是一种函数式编程的技术，它将一个接受多个参数的函数转化为一系列接受单一参数的函数。换句话说，柯里化将多参数函数拆分成多个一元函数的链式调用。

提高可重用性：通过固定部分参数，可以生成更具体的函数。
简化逻辑：特别是在函数式编程中，柯里化使得函数组合更容易。


柯里化的优缺点
优点：
更高的灵活性：可以按需分步传递参数。
提高代码的可重用性：部分应用（Partial Application）让函数更易复用。
简化函数组合：在函数式编程中，柯里化让函数链式调用变得自然。
缺点：
增加了复杂性：对于不熟悉的开发者，柯里化可能让代码难以理解。
性能问题：过多的嵌套函数调用可能导致栈深度增加，影响性能。
柯里化 vs 部分应用
柯里化 是将一个多参数函数转换为多个嵌套的一元函数。
部分应用（Partial Application） 是将一个函数的一部分参数固定，生成一个新的函数。



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