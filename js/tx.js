const axios = require('axios');

// 生成随机 4 位数字字符串
//
function random4Digit() {
    return Math.floor(1000 + Math.random() * 9000).toString();
}

async function run() {

    for (let i = 0; i < 10; i++) {
        uname="user"+random4Digit();

        try {
            baseurl="http://18.136.123.232:8889"
           // baseurl="http://localhost:888"
            url = baseurl+'/apiv1/admin/wlt/adjust?accountSubType=EMoney&transactionCode=adjst_crdt&uname=uuuu6666&adjustAmount=55&remark=xxxx';
           console.log(url)
            const response = await axios.get(url);
            console.log(`Request ${i + 1} - Status: ${response.status}`);
        } catch (error) {
            console.error(`Request ${i + 1} - Error:`, error.message);
        }
    }
}

run();
