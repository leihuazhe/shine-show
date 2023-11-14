var http = require('http'),
    httpProxy = require('http-proxy'),
    express = require('express');

// create a server
var app = express();
var proxy = httpProxy.createProxyServer({target: 'http://81.68.186.193:8084', ws: true});
var server = require('http').createServer(app);

// proxy HTTP GET / POST
app.get('/', function (req, res) {
    console.log("proxying GET request", req.url);
    proxy.web(req, res, {});
});
app.post('/', function (req, res) {
    console.log("proxying POST request", req.url);
    proxy.web(req, res, {});
});

// Proxy websockets
server.on('upgrade', function (req, socket, head) {
    console.log("proxying upgrade request", req.url);
    proxy.ws(req, socket, head);
});

// serve static content
app.use('/', express.static(__dirname + "/public"));

server.listen(9000);
