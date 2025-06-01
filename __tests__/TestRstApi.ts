// testRest.spec.ts  ..
import axios from 'axios';
import fs from 'fs';
import path from 'path';

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
