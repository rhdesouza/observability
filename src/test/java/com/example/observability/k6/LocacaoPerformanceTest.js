import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import { getAuthHeaders } from './config/AuthConfig.js'

/*
* Command: docker run --rm -i grafana/k6 run VeiculoPerformanceTest.js
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
  let idVeiculoRandom = randomIntBetween(1,30);
  let idClienteRandom = randomIntBetween(1,10);
  let idLocationRandom = randomIntBetween(1,getLocation());

  let myObjects = http.post(`${BASE_URL}/api/locacao/rent/${idClienteRandom}/${idVeiculoRandom}`, null, getAuthHeaders());
  let myObjectsDevolucao = http.post(`${BASE_URL}/api/locacao/devolution/${idLocationRandom}`, null, getAuthHeaders());

  const checkRes = check(myObjects, {
    'End-Point: /api/locacao/rent/ 200 or 404': (r) => r.status === 200 || r.status === 404
  });

  const checkResDevolucao = check(myObjectsDevolucao, {
    'End-Point: /api/devolution/ 200 or 404': (r) => r.status === 200 || r.status === 404
  });

  sleep(1);
};

function getLocation(){
  let locationsObj = http.get(`${BASE_URL}/api/locacao`, getAuthHeaders());
  if (locationsObj.status == '404'){
    return 1;
  }

  let locationsSize = JSON.parse(locationsObj.body);
  return locationsSize.length;
}