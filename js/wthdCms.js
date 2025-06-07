const axios = require('axios');

// 生成随机 4 位数字字符串
//
function random4Digit() {
    return Math.floor(1000 + Math.random() * 9000).toString();
}

async function run() {
    baseurl="http://18.136.123.232:8889"
      baseurl="http://localhost:888"
    url = baseurl+'/apiv1/cms/WthdrCmsHdl';


    console.log(url)
    const jwtToken = "your-jwt-token"; // 这里填你的 JWT

    console.log("请求地址:", url);

    const response = await axios.get(url, {
        headers: {
            Authorization: `Bearer ${jwtToken}`
        }
    });
    console.log(`Request  - Status: ${response.status}`);

}

run();
