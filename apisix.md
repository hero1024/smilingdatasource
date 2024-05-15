### APISIX

```
docker-compose --version
git clone -b release/apisix-3.6.0 https://github.com/apache/apisix-docker.git
cd /usr/local/apisix-docker/example
docker-compose -p docker-apisix up -d


###
PUT http://127.0.0.1:9180/apisix/admin/routes/1
X-API-KEY: edd1c9f034335f136f87ad84b625c8f1
Content-Type: application/json


{
  "name": "glm_auth",
  "uri": "/glm/*",
  "plugins": {
    "forward-auth": {
      "uri": "http://192.168.134.212:8082/security/auth",
      "request_headers": ["Authorization"],
      "upstream_headers": ["X-User-ID"],
      "client_headers": ["Location"]
    },
    "cors": {},
    "prometheus":{
      "prefer_name": true
    }
  },
  "upstream": {
    "type": "roundrobin",
    "nodes": {
      "10.40.241.6:17862": 1
    }
  }
}




###

PUT http://127.0.0.1:9180/apisix/admin/routes/2
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
    "cors": {},
    "prometheus":{
      "prefer_name": true
    }
  },
  "uris": [
    "/smiling/question/*",
    "/smiling/dbsource/*",
    "/smiling/education/*"
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

