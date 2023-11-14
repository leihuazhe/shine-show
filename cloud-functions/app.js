const express = require('express')
const {
    createProxyMiddleware
} = require('http-proxy-middleware');
const app = express()
const port = 9000

app.use('/', createProxyMiddleware({
    target: 'http://39.108.110.17:8089',
    changeOrigin: true,
    headers: {
        'x-custom-header' : 'maple mardown'
    },
    onProxyRes: function (proxyRes, req, res) {
        proxyRes.headers['Access-Control-Allow-Origin'] = '*';
    }
}));

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})