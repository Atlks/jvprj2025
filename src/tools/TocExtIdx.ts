import * as fs from "fs";

// Extract table of contents from file
/**
 * ver 24.1227
 */

// 示例调用  C:\0prj\paymentJava2024\build.gradle
var filePath = ""; // 替换为你的文件路径
filePath="C:\\0prj\\paymentJava2024\\doc2501\\投资决策技术.md";
const searchString = "= - ."; // 替换为你要查找的字符串
console.log("-----------TOC INDEX------------");
ExtrctTOC(filePath, searchString);



/**
 * 读取文件内容，逐行检查并打印包含特定字符串的行。
 * @param filePath 文件路径
 * @param searchString 要查找的特定字符串
 */
function ExtrctTOC(filePath: string, searchString: string): void {
    try {
        // 读取文件内容并按行分割
        const lines = fs.readFileSync(filePath, "utf-8").split("\n");

        lines.forEach((line, index) => {
            if (isStartWith(line,searchString)) {
                let lineNum = index + 1;
                console.log(line );
            }
        });
    } catch (error) {
        console.error(`Error reading file: ${error.message}`);
    }
}




/**
 * 判断是否包含任何一个关键词
 * @param line
 * @param searchKeywords  多个要搜索的关键词，用空格分割
 */
function includesx(line:string, searchKeywords: string) {
    // 将关键词字符串按空格分割成数组
    const keywords = searchKeywords.split(" ");

    // 检查是否有任意一个关键词存在于 line 中
    return keywords.some(keyword => line.includes(keyword));
}

function isStartWith(line, searchKeywords: string) {
    // 将关键词字符串按空格分割成数组
    const keywords = searchKeywords.split(" ");

    // 检查是否有任意一个关键词存在于 line 中
    return keywords.some(keyword => line.startsWith(keyword));
}

