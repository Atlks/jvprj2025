package test;

public class MyClassLoader extends ClassLoader {

    /**
     * Creates a new class loader using the {@code ClassLoader} returned by
     * the method {@link #getSystemClassLoader()
     * getSystemClassLoader()} as the parent class loader.
     *
     * <p> If there is a security manager, its {@link


     * a security exception.  </p>
     *
     * @throws SecurityException If a security manager exists and its
     *                           {@code checkCreateClassLoader} method doesn't allow creation
     *                           of a new class loader.
     */
    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {

        super(parent); // 继承当前 ClassLoader
    }


    public Class<?> defineClassFromByteArray(byte[] bytecode) {
        return defineClass(null, bytecode, 0, bytecode.length);
    }

    public Class<?> defineClassFromByteArray(String name,byte[] bytecode) {
        return defineClass(name, bytecode, 0, bytecode.length);
    }

    //给系统classload定义一个类
    public Class<?> defineClassFromByteArrayToSysClsLdr(String name,byte[] bytecode) {
        Class<?>  cls=   super.defineClass(name, bytecode, 0, bytecode.length);
        return cls;
    }
}
