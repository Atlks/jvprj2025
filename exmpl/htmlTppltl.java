package ztest;

import entityx.usr.Usr;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.thymeleaf.templateresolver.FileTemplateResolver;

import static util.algo.JarClassScanner.getPrjPath;

public class htmlTppltl {

    public static void main(String[] args) throws IOException {
          TemplateEngine templateEngine;

     //   testVar();


        List<Usr> users = Arrays.asList(
                new Usr("alice" ),
                new Usr("bob" )
        );

        Context context = new Context();
        context.setVariable("users", users);
        String tmpleFileName = "table";


        System.out.println( rend(tmpleFileName, context ));  ;
    }

    private static void testVar() {
        // 3. 创建上下文，填充数据
        Context context = new Context();
        context.setVariable("name", "世界");
        String tmpleFileName = "hello";

        // rend(tmpleFileName, context);
    }

    /**
     * 渲染模板并输出到字符串，，，
     * @param tmpleFileName     adm/lgn.htm
     * @param context
     * @return
     * @throws IOException
     */
    public static String rend(String tmpleFileName, Context context) throws IOException {
        // 1. 配置模板解析器（从文件读取）
        FileTemplateResolver resolver = new  FileTemplateResolver();
        resolver.setPrefix( getPrjPath() + "/res/templates/");     // 模板目录
        resolver.setSuffix(".htm");         // 模板后缀
        resolver.setTemplateMode("HTML");    // 模板模式
        resolver.setCharacterEncoding("UTF-8");

        // 2. 创建模板引擎
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);


        // 3. 渲染模板并输出到字符串
        try (StringWriter writer = new StringWriter()) {
            engine.process(tmpleFileName, context, writer);
            return writer.toString();  // 返回字符串
        }
    }


    public static String renderHtml(String tmpleFileName, Context context) throws IOException {
        // 1. 配置模板解析器（从文件读取）
        FileTemplateResolver resolver = new  FileTemplateResolver();
        resolver.setPrefix( getPrjPath() + "/res/templates/");     // 模板目录
        resolver.setSuffix(".html");         // 模板后缀
        resolver.setTemplateMode("HTML");    // 模板模式
        resolver.setCharacterEncoding("UTF-8");

        // 2. 创建模板引擎
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);


        // 3. 渲染模板并输出到字符串
        try (StringWriter writer = new StringWriter()) {
            engine.process(tmpleFileName, context, writer);
            return writer.toString();  // 返回字符串
        }
    }
//    private static void rend(String tmpleFileName, Context context) throws IOException {
//        // 1. 配置模板解析器（从文件读取）
//        FileTemplateResolver resolver = new  FileTemplateResolver();
//        resolver.setPrefix( getPrjPath() + "/res/templates/");     // 模板目录
//        resolver.setSuffix(".htm");         // 模板后缀
//        resolver.setTemplateMode("HTML");    // 模板模式
//        resolver.setCharacterEncoding("UTF-8");
//
//        // 2. 创建模板引擎
//        TemplateEngine engine = new TemplateEngine();
//        engine.setTemplateResolver(resolver);
//
//
//        // 4. 渲染模板并输出到文件
//        try (Writer writer = new FileWriter("out.htm")) {
//
//            engine.process(tmpleFileName, context, writer); // hello.html -> out.htm
//        }
//
//        System.out.println("模板渲染完成，已输出到 out.htm");
//    }
}
