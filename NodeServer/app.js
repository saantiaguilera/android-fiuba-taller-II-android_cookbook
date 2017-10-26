const express = require("express");
const morgan = require('morgan');
const bodyParser = require('body-parser');
const app = express();

app.use(morgan('combined'));
app.use(bodyParser.json());

app.get("/some/url", function(req, res) {
  res.status(200).sendfile('some_url_get.json');
});

app.post("/some/url", function(req, res) { 
  console.log(req.body);
  res.status(201).send(req.body);
});

app.get("/fail/url", function(req, res) {
  res.status(503).send(
    {
      message: "This is supposed to fail... So it failed"
    }
  );
});

app.listen(3000);