### APISIX

```

###
PUT http://127.0.0.1:9180/apisix/admin/routes
X-API-KEY: edd1c9f034335f136f87ad84b625c8f1
Content-Type: application/json


{
  "id": "usr",
  "uri": "/security/*",
  "upstream": {
    "type": "roundrobin",
    "nodes": {
      "192.168.134.212:8082": 1
    }
  }
}




###

PUT http://127.0.0.1:9180/apisix/admin/routes/1
X-API-KEY: edd1c9f034335f136f87ad84b625c8f1
Content-Type: application/json


{
  "name": "smiling_auth",
  "plugins": {
    "forward-auth": {
      "uri": "http://192.168.134.212:8082/security/auth",
      "request_headers": ["Authorization"],
      "upstream_headers": ["X-User-ID"],
      "client_headers": ["Location"]
    },
    "prometheus":{
      "prefer_name": true
    }
  },
  "uris": [
    "/smiling/question/*",
    "/smiling/dbsource/*"
  ],
  "upstream": {
    "type": "roundrobin",
    "nodes": {
      "192.168.134.212:8081": 1
    }
  }
}

###


###

```

