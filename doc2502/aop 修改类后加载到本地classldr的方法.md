=//反序列化到当前 ClassLoader
---------使用自定义类加载器加载字节码
MyClassLoader myClassLoader = new MyClassLoader();
Class<?> modifiedClass = myClassLoader.defineClassFromByteArray(aClass.getName(), bytecode);
Object instance = modifiedClass.getConstructor().newInstance();


            // 反序列化到当前 ClassLoader
            ByteArrayInputStream bis = new ByteArrayInputStream(serialize(instance));
            ObjectInputStream in = new ObjectInputStream(bis);
            Object deserializedInstance = in.readObject();
            Class<?> modifiedClass2 = deserializedInstance.getClass();
            return modifiedClass2;


=使用接口进行强类型转换
以使用 MyInterface 进行强类型转换


解决方案
1. 创建自定义 ClassLoader
   因为 AppClassLoader 无法 defineClass()，我们必须创建一个新的 ClassLoader，然后让它继承 AppClassLoader。