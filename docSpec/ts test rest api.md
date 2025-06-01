

C:\Users\Administrator\IdeaProjects\OptiBot\__tests__\TestRstApi.ts

must   tests__ dir


# add test model
npm install --save-dev jest ts-jest @types/jest typescript

----add test cfg
步骤 2：初始化 jest 和 tsconfig
bash
Copy
Edit
npx ts-jest config:init
这会生成：

jest.config.js

tsconfig.json

---root dir

✅ 方案 1：启用 esModuleInterop（推荐）
编辑你的 tsconfig.json，添加或修改这两项：

json
Copy
Edit
{
"compilerOptions": {
"esModuleInterop": true,
"allowSyntheticDefaultImports": true
}
}
----ts


let token: string;
const baseURL = 'http://localhost:888';

beforeAll(() => {
const jwtPath = path.join(__dirname, '../test/jwt.txt');
token = fs.readFileSync(jwtPath, 'utf-8').trim();
});

test('QryFundDetailHdl should return 200', async () => {
const response = await axios.get(`${baseURL}/apiv1/wlt/QryFundDetailHdl`, {
headers: {
Authorization: `Bearer ${token}`,
},
});

    expect(response.status).toBe(200);
});


---test cmd
npx jest

or run in ide
