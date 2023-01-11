import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import { getAuthHeaders } from './config/AuthConfig.js'

/*
* Command: docker run --rm -i grafana/k6 run ClientPerformanceTest.js
*/

export let options = {
  stages: [
    { duration: '1m', target: 10 },
    { duration: '3m', target: 15},
    { duration: '1m', target: 20 },
  ],
  thresholds: {
    http_req_duration: ['p(99)<2000'], // 99% of requests must complete below 1.5s
  },
};

const BASE_URL = 'http://localhost:8080';

export default () => {
  let idClienteRandom = randomIntBetween(1,20);

  let myObjects = http.get(`${BASE_URL}/api/cliente/${idClienteRandom}`, getAuthHeaders());

  const checkRes = check(myObjects, {
    'End-Point: /api/cliente/ 200 or 404': (r) => r.status === 200 || r.status === 404  ,
  });

  sleep(1);
};