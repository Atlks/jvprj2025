import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class AutoRestartApp {
    public static void main(String[] args) throws Exception {
        String pathToWatch = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\src";
        // 监听的文件夹路径
        watchDirectory(pathToWatch);
    }

    public static void watchDirectory(String dirPath) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(dirPath);

        // 递归注册所有子目录
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("监视文件变更: " + dirPath);

        while (true) {
            WatchKey key = watchService.take(); // 阻塞等待事件
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                Path filePath = (Path) event.context();

                if (kind == StandardWatchEventKinds.ENTRY_MODIFY && filePath.toString().endsWith(".java")) {
                    System.out.println("检测到代码变更：" + filePath);
                    restartApplication();
                }
            }
            key.reset();
        }
    }


    // 这里好像 -classpath参数没有收集到
    public static void restartApplication() throws IOException {
        System.out.println("正在重启应用...");

        // 获取 Java 运行时路径
        String javaBin = "C:\\Users\\attil\\.jdks\\corretto-21.0.6\\bin\\java.exe";
        String filePathClassps = "C:\\doc_2024_md\\classpath.txt";




        // 获取当前 JVM 运行的参 数     // 这里好像 -classpath参数没有收集到
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
         //判断如果String里面包含空格，那么list的元素使用双引号包起来
        // 创建一个新的列表来存储处理后的参数
        List<String> processedArgs = new ArrayList<>();

        for (String arg : inputArguments) {
            // 判断如果参数包含空格，使用双引号包起来
            if (arg.contains(" ")) {
                processedArgs.add("\"" + arg + "\"");
            } else {
                processedArgs.add(arg);
            }
        }

        // 拼接成一个完整的命令行参数字符串
        String vmArgs = String.join(" ", processedArgs);
      //  String vmArgs = String.join(" ", processedArgs);
        System.out.println("vmargs======\n"+vmArgs);
        // 获取主类（entry point）
        String mainClass = ManagementFactory.getRuntimeMXBean().getSystemProperties().get("sun.java.command");
        var classpathPrm=getClasspath(filePathClassps);

      //  classpathPrm="xxx.jar";
    //    classpathPrm="\""+classpathPrm+"\"";
        System.out.println("重启命令：" + javaBin + " " + vmArgs + " " + "-classpath "+classpathPrm+" "+ mainClass);



        // 创建新进程，重新运行当前程序
        ProcessBuilder builder = new ProcessBuilder(javaBin, vmArgs, "-classpath", classpathPrm, mainClass);
        builder.start();

        System.exit(0); // 退出当前进程
    }

    private static String getClasspath(String filePath) throws IOException {
        // 读取文件内容
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // 查找以 -classpath 开头的行
        for (String line : lines) {
            if (line.startsWith("-classpath")) {
               // return line;
                        String classpath = line.substring("-classpath".length()).trim();
return  classpath;
              //  System.out.println("Classpath: " + classpath);
            }
        }

        return "missss.jar";
    }
}
