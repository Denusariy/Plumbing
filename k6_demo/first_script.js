import http from 'k6/http';
// import {sleep} from 'k6';
import {check} from 'k6';

export const options = {
    scenarios: {
        constant_request_rate: {
            executor: 'constant-arrival-rate',
            rate: 1000,
            timeUnit: '1s', // 1000 iterations per second, i.e. 1000 RPS
            duration: '5m',
            preAllocatedVUs: 50, // how large the initial pool of VUs would be
            maxVUs: 200, // if the preAllocatedVUs are not enough, we can initialize more
        },
    }

    // stages: [
    //     { duration: '5m', target: 100 },
    //     { duration: '30m', target: 100 },
    //     { duration: '5m', target: 0 }
    // ]
}
// https://k6.io/docs/using-k6/scenarios/executors/externally-controlled/
// https://k6.io/docs/results-output/real-time/prometheus-remote-write/
// https://k6.io/blog/how-to-generate-a-constant-request-rate-with-the-new-scenarios-api/
// external control
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
    // sleep(1);
}