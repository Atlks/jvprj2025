package util.misc;

import jakarta.ws.rs.Path;
import util.annos.Paths;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static util.misc.util2026.scanAllClass;

public class RestUti {

    public static Map<String, Method> pathMthMap = new HashMap<>();

    public static void iniRestPathMthMap() {
        Consumer<Class> fun = aClass -> {
            if (aClass.getName().startsWith("api") || aClass.getName().startsWith("handler")) {
               Method[] methods = aClass.getDeclaredMethods();
               for (Method mth : methods)
                {
                    //  var bean=getBeanFrmSpr(aClass);
//                    var path = getPathFromBean(aClass);
//                    System.out.println("pathMap(path=" + path + ",aClass=" + aClass.toString());
//                    //   server.createContext(path, (HttpHandler) bean);
//                    pathMap.put(path, aClass);
//                    if (aClass.getName().contains("RechargeHdr"))
//                        System.out.println("D835");
                    var path_pkgNclsname = getLastPkgNClsName(aClass);
                    var MthdName=mth.getName();
                    var pathname=path_pkgNclsname+"/"+MthdName;
                    pathMthMap.put(pathname, mth);
                    System.out.println("pathMap(path=" + pathname + ",mth=" + mth.toString());


                    //add rs path
                    if(mth.getName().contains("getCptch") )
                        System.out.println("D835");
                    String path22=getPathFromFun(mth);
                    pathMthMap.put(path22, mth);
                    System.out.println("pathMap(path=" + path22 + ",mth=" + mth.toString());


                    //proce paths
                    String[] getPathsFromBeanRzt = getPathsFromFun(mth);
                    for (String p : getPathsFromBeanRzt) {

                        pathMthMap.put(p, mth);
                        System.out.println("pathMap(path=" + p + ",mth=" + mth.toString());

                    }
                }


            }
        };
        System.out.println("====start createContext");
        scanAllClass(fun);
        System.out.println("====end createContext");
    }

    public static String getPathFromFun(Method mth) {


        if (mth.isAnnotationPresent(Path.class)) {
            Path mapping = mth.getAnnotation(Path.class);
          //  assert mapping != null;
            return mapping.value();  // 可能有多个路径
        }

        return "";


    }


    public static String[] getPathsFromFun(Method mth) {


        if (mth.isAnnotationPresent(Paths.class)) {
            Paths mapping = mth.getAnnotation(Paths.class);
            assert mapping != null;
            return mapping.value();  // 可能有多个路径
        }

        return new String[]{};


    }

    public static String getLastPkgNClsName(Class<?> aClass) {
        if (aClass == null) {
            return "";
        }

        Package pkg = aClass.getPackage();
        String packageName = pkg != null ? pkg.getName() : "";
        String[] parts = packageName.split("\\.");
        String lastPkg = parts.length > 0 ? parts[parts.length - 1] : "";
        return "/" + lastPkg +"/"+aClass.getSimpleName() + "";
    }


}
