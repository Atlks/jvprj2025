


/**
* ioc的实现，，，reg n getbean fun
  */
  public class SimpleContainer {
  private static final Map<String, Supplier<Object>> registryMap = new HashMap<>();

  // 注册对象构造方式（可支持懒加载）
  public static void registerObj(String beanNname, Supplier<Object> creator) {
  registryMap.put(beanNname, creator);
  }

  // 获取对象实例（每次调用 new，或内部做缓存）
  @SuppressWarnings("unchecked")
  public static <T> T getObj(String beanname) {
  Supplier<Object> creator = registryMap.get(beanname);
  if (creator == null) throw new RuntimeException("No such bean: " + beanname);
  return (T) creator.get();
  }
  }


