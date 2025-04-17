
//  node docRestApi/server.js
// http://localhost:8080/

const express = require('express');
const path = require('path');

const app = express();
const PORT = 8080;

// 设置当前目录为静态目录
app.use(express.static(path.join(__dirname)));

app.listen(PORT, () => {
  console.log(`静态文件服务器已启动：http://localhost:${PORT}`);
});
