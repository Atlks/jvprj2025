


多余的注册

检测 注册名称

for (String beanName : context.getBeanDefinitionNames()) {
System.out.println("..已注册 Bean：" + beanName);
}

固然有俩个

因为一个使用了regsiter注册了，一个自动使用了注解注册

//@Component


解决方法，去除一个即可，，去除注解，，手动扫描注册


