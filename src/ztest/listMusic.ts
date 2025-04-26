
// #xx.ts
//  "C:\Program Files\nodejs\npm.cmd" install ts-node --save-dev
import fs from 'fs';
import path from 'path';

var dir="C:\\Users\\Lenovo\\OneDrive\\音乐\\haotin songs 2025"

var files=getFiles(dir)

logx(files)
//console.log(files);

function getFiles(dir: string) {
    console.log(dir)
     // 用 fs.readdirSync 读取目录下的文件和文件夹
     const fileNames: string[] = [];
    
     try {
         const filesInDir = fs.readdirSync(dir);
 
         // 遍历目录中的每个文件或子目录
         for (const file of filesInDir) {
             const fullPath = path.join(dir, file);
             const stat = fs.statSync(fullPath);
 
             if (stat.isFile()) {
                 // 如果是文件，则加入文件列表
                 fileNames.push(fullPath);
             }
         }
     } catch (error) {
         console.error("Error reading directory:", error);
     }
 
     return fileNames;
}

/**
 * 返回json序列化后的对象，格式化美化json字符串
 * @param obj - 需要格式化的对象
 * @returns 格式化后的JSON字符串
 */
function logx(obj: any) {
    console.log( JSON.stringify(obj, null, 4));  // null为过滤器，4为缩进空格数
}

