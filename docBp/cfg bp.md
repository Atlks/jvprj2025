
/**
** 该方法会依次检查以下路径来寻找配置文件：
*      * 1. JVM 系统属性 "dbcfg" 指定的路径
*      * 2. /根目录下的 "cfg" 目录
*      * 3. 项目目录路径下的 "cfg" 目录
*      * 4. 编译后 target 目录下的 "cfg" 目录
*      * 5. 作为资源从 classpath 加载
* @param cfgFileName
* @return
* @throws FileNotFoundException
*/
@NotNull
private static InputStream getCfgInputStream(@NotBlank String cfgFileName) throws FileNotFoundException {
InputStream inputStream = null;
// 使用相对路径读取文件
//   Path path = Paths.get("cfg/dbcfg.ini");
//   String content = new String(Files.readAllBytes(path));
// 获取系统属性中定义的 dbcfg 配置文件路径
String sysPropDbcfg = System.getProperty("dbcfg");
if (!isblank(sysPropDbcfg)) {
System.out.println(" iniCfgFrmCfgfile(),rd sys prop,dbcfg=" + sysPropDbcfg);
inputStream = new FileInputStream(sysPropDbcfg);
} else {
//-root dir mode
String rootDirMode = "/cfg/" + cfgFileName;
if (new File(rootDirMode).exists()) {
System.out.println(" iniCfgFrmCfgfile().rootDir,,dbcfg=" + rootDirMode);
inputStream = new FileInputStream(rootDirMode);
} else {
//---prj path
// 尝试从项目路径的 cfg 目录获取配置文件
String prjDirMode = getPrjPath() + "/cfg/" + cfgFileName;
if (new File(prjDirMode).exists()) {
System.out.println(" iniCfgFrmCfgfile().prjDirMode blk,,dbcfg=" + prjDirMode);
inputStream = new FileInputStream(prjDirMode);
} else {
//--target dir mode
String targetDirMode = getTargetPath() + "/cfg/" + cfgFileName;
System.out.println(" iniCfgFrmCfgfile().targetDirMode blk,,dbcfg=" + targetDirMode);
if (new File(targetDirMode).exists()) {
inputStream = new FileInputStream(targetDirMode);
} else {
// 尝试从 classpath 加载资源文件
//-----jar mode
//  inputStream = new FileInputStream( targetDirMode);
inputStream = MyCfg.class.getClassLoader().getResourceAsStream(cfgFileName);
}
}
}
}
if(inputStream==null)
throw  new RuntimeException("cant find cfg");
return inputStream;
}

/**
* maven编译后目录结构是这样的
* /target/cfg/xx.cfg
* /target/jvprj.jar
* jvprj.jar 里面有个mainapi类，这个类要如何代码里面要要如何读取xx.cfg
*/
public static void iniCfgFrmCfgfile() throws FileNotFoundException {

        //test
     
            }
        }