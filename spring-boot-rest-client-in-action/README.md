# HTTP Rest Call



# Elastic Rest Async Call
```json

POST _bulk
{"index":{"_index":"restclient_test_1","_id":"1"}}
{"word":"스팀게임"}
{"index":{"_index":"restclient_test_1","_id":"2"}}
{"word":"스팀게임 추천"}
{"index":{"_index":"restclient_test_1","_id":"3"}}
{"word":"스팀게임 추천 2019"}
{"index":{"_index":"restclient_test_1","_id":"4"}}
{"word":"스팀게임 환불"}
{"index":{"_index":"restclient_test_1","_id":"5"}}
{"word":"스팀게임 싸게"}
{"index":{"_index":"restclient_test_1","_id":"6"}}
{"word":"스팀게임 순위"}
{"index":{"_index":"restclient_test_1","_id":"7"}}
{"word":"스팀게임 추천 2020"}
{"index":{"_index":"restclient_test_1","_id":"8"}}
{"word":"스팀게임 환불하는법"}

```


```json

PUT restclient_test_1
{
  "settings": {
    "index": {
      "number_of_shards": 1,
      "number_of_replicas": 1
    }
  },
   "mappings": {
      "properties": {
        "word": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword"
            }
          }
        }
    }
  }
}

```