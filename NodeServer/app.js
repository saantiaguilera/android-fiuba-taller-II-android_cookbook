const express = require("express");
const morgan = require('morgan');
const bodyParser = require('body-parser');
const app = express();

app.use(morgan('combined'));
app.use(bodyParser.json());

app.get("/some/url", function(req, res) {
  // Send the json file as response
  res.status(200).sendfile('some_url_get.json');
});

app.post("/some/url", function(req, res) { 
  // Mirror the body as response
  res.status(201).send(req.body);
});

app.get("/fail/url", function(req, res) {
  // Send a fail response of Service Unavailable
  res.status(503).send(
    {
      message: "This is supposed to fail... So it failed"
    }
  );
});

// Listen to port :3000
app.listen(3000);