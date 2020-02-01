const domain = 'localhost';
const port = '3000';
const clientId = '7303091';

export const AUTHORIZATION_LINK = 
    `https://oauth.vk.com/authorize?client_id=${clientId}&display=page&redirect_uri=http://${domain}:${port}/main&scope=friends&response_type=code&v=5.103`