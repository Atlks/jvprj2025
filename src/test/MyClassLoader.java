package test;

public class MyClassLoader extends ClassLoader {
    public Class<?> defineClassFromByteArray(byte[] bytecode) {
        return defineClass(null, bytecode, 0, bytecode.length);
    }

    public Class<?> defineClassFromByteArray(String name,byte[] bytecode) {
        return defineClass(name, bytecode, 0, bytecode.length);
    }
}
