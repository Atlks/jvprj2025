File srch tool in   php,dart   by   mdfdt ,kwds ,extname,location


/**
* 搜索并处理指定目录中修改日期在指定时间之后的文件
* - 支持按扩展名筛选（例如 java, xml）
* - 支持按关键字内容匹配
* - 复制保留原始目录结构（可选）
*
* @param modifiedAfter 修改时间必须晚于此时间
* @param extnames 扩展名筛选，例如："java,xml"
* @param sourceDirPath 源文件目录路径
* @param targetDirPath 匹配文件要复制到的目标路径（保留目录结构）
* @param srchKwds 要搜索的关键词（用空格分隔，全部都必须匹配）
  */
C:\0prj\jvprj2025\src\tools\srchFils.kt

jsPrjMng/lib/fulltxtSrchV2.php

C:\0prj\paymentJava2024\tool\srch.dart

void main() async {
  var mdfDate = '2020-11-11';
  final modifiedAfter = DateTime.parse(mdfDate); // 指定日期
  var sourceDirPath = 'C:\\0prjNtpc\\digital-bet-service'; // 主目录路径
  var targetDirPath = '/bkPrjDBS241108'; // 输出目录路径
  sourceDirPath = "C:\\0prjNtpc\\digital-bet-service - p3";
  targetDirPath = "bkPrjDBS241112asrch";
  var extnames = "java,xml";
  var srchKwds = "global-game-api-config";
  await srch(modifiedAfter, extnames, sourceDirPath, targetDirPath, srchKwds);
}

