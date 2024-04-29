### APISIX

```

###
PUT http://127.0.0.1:9180/apisix/admin/routes
X-API-KEY: edd1c9f034335f136f87ad84b625c8f1
Content-Type: application/json


{
  "id": "usr",
  "uri": "/usr/*",
  "upstream": {
    "type": "roundrobin",
    "nodes": {
      "10.0.10.72:8080": 1
    }
  }
}


###
PUT http://127.0.0.1:9180/apisix/admin/routes
X-API-KEY: edd1c9f034335f136f87ad84b625c8f1
Content-Type: application/json


{
  "id": "smiling",
  "uri": "/smiling/*",
  "upstream": {
    "type": "roundrobin",
    "nodes": {
      "10.0.10.72:8081": 1
    }
  }
}



###

PUT http://127.0.0.1:9180/apisix/admin/routes/1
X-API-KEY: edd1c9f034335f136f87ad84b625c8f1
Content-Type: application/json


{
  "name": "smiling-dbsource",       ## 给这个路由配置个名字
  "plugins": {
    "forward-auth": {
      "uri": "http://10.0.10.72:8080/usr/auth",
      "request_headers": ["Authorization"],
      "upstream_headers": ["X-User-ID"],
      "client_headers": ["Location"]
    },
    "prometheus":{                    ## 启用 prometheus
            "prefer_name": true       ## 为"true"时，Prometheus 指标中，打印路由/服务名称而不是 ID
        }
  },
  "uri": "/smiling/dbsource/*",
  "upstream": {
    "type": "roundrobin",
    "nodes": {
      "10.0.10.72:8081": 1
    }
  }
}


###

```

