const express = require('express')
const {
    createProxyMiddleware
} = require('http-proxy-middleware');
const app = express()
const port = 9000

app.use('/', createProxyMiddleware({
    target: 'http://81.68.186.193:8084',
    changeOrigin: true,
    headers: {
        'x-custom-header' : 'QUnogZrokL0='
    },
    onProxyRes: function (proxyRes, req, res) {
        proxyRes.headers['Access-Control-Allow-Origin'] = '*';
    }
}));

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})