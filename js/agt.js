const axios = require('axios');

// 生成随机 4 位数字字符串
function random4Digit() {
    return Math.floor(1000 + Math.random() * 9000).toString();
}

async function regAgt(uname,agt) {
    try {
          baseurl = 'http://18.136.123.232:8889';
        //  baseurl="http://localhost:888"
        url = baseurl + '/apiv1/reg?uname=' + uname + '&pwd=uuuuuuuu&invtr=' +agt ;
        console.log(url)
        const response = await axios.get(url);
        console.log(`Request   - Status: ${response.status}`);
    } catch (error) {
        console.error(`Request   - Error:`, error.message);
    }
}

async function run() {
    await regAgt("uuuu1111","uuuu6666");
    await regAgt("uuuu2222","uuuu1111");
    await regAgt("uuuu3333","uuuu2222");
    await regAgt("uuuu4444","uuuu3333");
}

run();
