import http from 'k6/http';
import {sleep} from 'k6';

export const options = {
    vus: 1000,
    duration: '20s'
};

export default function () {
    const url = 'http://localhost:7777/api/v1/houses/repair-house'
    const payload = JSON.stringify({
        "houseId": 1,
        "plumberId": 1,
        "repairPrice": 5
    });
    const params = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    http.post(url, payload, params);
}