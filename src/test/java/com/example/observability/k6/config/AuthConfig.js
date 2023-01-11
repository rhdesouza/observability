import encoding from 'k6/encoding';

const USERNAME = 'admin';
const PASSWORD = 'admin';

const encodedCredentials = encoding.b64encode(`${USERNAME}:${PASSWORD}`);

let authHeaders = {
    headers: {
        Authorization: `basic ${encodedCredentials}`,
    },
};

export function getAuthHeaders(){
    return authHeaders;
}
    



