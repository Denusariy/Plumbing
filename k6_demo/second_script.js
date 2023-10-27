import http from 'k6/http';
import {check} from 'k6';

export const options = {
    scenarios: {
        contacts: {
            executor: 'ramping-arrival-rate',

            // Start iterations per `timeUnit`
            startRate: 100,

            // Start `startRate` iterations per minute
            timeUnit: '1s',

            // Pre-allocate necessary VUs.
            preAllocatedVUs: 100,

            stages: [
                { target: 250, duration: '1m' },

                { target: 500, duration: '5m' },

                { target: 0, duration: '1m' },
            ],
        },
    },
};

export default function() {
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
    const response =  http.post(url, payload, params);
    check(response, {
        'is http status code 200': (r) => r.status === 200
    });
    check(response, {
        'response should have the correct repairPrice': (r) => r.json().repairPrice === 5,
    });
}
