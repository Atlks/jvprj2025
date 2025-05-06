

==watchDirectory more easy
C:\Users\attil\IdeaProjects\jvprj2025\src\handler.mainx.AutoRestartApp.java
/**
* // 创建新进程，重新运行 当前程序
* @throws IOException
* @throws InterruptedException
*/

    public static void restartApplication() throws IOException, InterruptedException {
        System.out.println("正在重启应用...");

        // 获取 Java 运行时路径
        String javaBin = "C:\\Users\\attil\\.jdks\\corretto-21.0.6\\bin\\java.exe";
        String filePathClassps = "C:\\doc_2024_md\\classpath.txt";


        // 获取当前 JVM 运行的参 数     // 这里好像 -classpath参数没有收集到
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        var classpathPrm = getClasspath(filePathClassps);
        String mainClass = ManagementFactory.getRuntimeMXBean().getSystemProperties().get("sun.java.command");

        // 组装命令
        List<String> command = new ArrayList<>();
        command.add(javaBin);
        command.addAll(inputArguments);
        command.add("-classpath");
        command.add(classpathPrm);
        command.add(mainClass);
        System.out.println(encodeJson(command));

        // 创建新进程，重新运行当前 程序
        ProcessBuilder builder = new ProcessBuilder(command);
        var process = builder.start();

        // 捕获错误日志，避免新进程启动失败但没有日志
        printErr(process);


        int exitCode = process.waitFor();

        //here if err exit  // 创建新进程，重新运行 当前程序
        System.out.println("新进程退出代码: " + exitCode);
        System.exit(0); // 退出当前进程sdfd v





    }
