# Kafka Spring Boot 

### Kafka Docker 구성
```yaml
version: '3.6'

services:
  akhq:
    image: tchiotludo/akhq:0.15.0
    environment:
      AKHQ_CONFIGURATION: |
        akhq:
          connections:
            docker-kafka-server:
              properties:
                bootstrap.servers: "kafka:19092"
              schema-registry:
                url: "http://schema-registry:8085"
              connect:
                url: "http://connect:8083"
    ports:
      - 9000:8080
    links:
      - kafka
      - schema-registry

  zookeeper:
    image: confluentinc/cp-zookeeper:5.5.2
    volumes:
      - C://docker/zookeeper-data:/var/lib/zookeeper/data
      - C://docker/zookeeper-log:/var/lib/zookeeper/log
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:5.5.2
    ports:
      - "9092:9092"
      - "19092:19092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_NUM_PARTITIONS: 12
      KAFKA_COMPRESSION_TYPE: gzip
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka:19092,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
      KAFKA_CONFLUENT_SUPPORT_METRICS_ENABLE: 'false'
      KAFKA_JMX_PORT: 9091
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'
    volumes:
      - C://docker/kafka-data:/var/lib/kafka/data
    links:
      - zookeeper

  schema-registry:
    image: confluentinc/cp-schema-registry:5.5.2
    depends_on:
      - kafka
    environment:
      SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: "PLAINTEXT://kafka:19092"
      SCHEMA_REGISTRY_HOST_NAME: schema-registry
      SCHEMA_REGISTRY_LISTENERS: "http://0.0.0.0:8085"
      SCHEMA_REGISTRY_LOG4J_ROOT_LOGLEVEL: INFO

  connect:
    image: confluentinc/cp-kafka-connect:5.5.2
    depends_on:
      - kafka
      - schema-registry
    environment:
      CONNECT_BOOTSTRAP_SERVERS: kafka:19092
      CONNECT_REST_PORT: 8083
      CONNECT_REST_LISTENERS: http://0.0.0.0:8083
      CONNECT_REST_ADVERTISED_HOST_NAME: connect
      CONNECT_CONFIG_STORAGE_TOPIC: __connect-config
      CONNECT_OFFSET_STORAGE_TOPIC: __connect-offsets
      CONNECT_STATUS_STORAGE_TOPIC: __connect-status
      CONNECT_GROUP_ID: "kafka-connect"
      CONNECT_KEY_CONVERTER_SCHEMAS_ENABLE: "true"
      CONNECT_KEY_CONVERTER: io.confluent.connect.avro.AvroConverter
      CONNECT_KEY_CONVERTER_SCHEMA_REGISTRY_URL: http://schema-registry:8085
      CONNECT_VALUE_CONVERTER_SCHEMAS_ENABLE: "true"
      CONNECT_VALUE_CONVERTER: io.confluent.connect.avro.AvroConverter
      CONNECT_VALUE_CONVERTER_SCHEMA_REGISTRY_URL: http://schema-registry:8085
      CONNECT_INTERNAL_KEY_CONVERTER: org.apache.kafka.connect.json.JsonConverter
      CONNECT_INTERNAL_VALUE_CONVERTER: org.apache.kafka.connect.json.JsonConverter
      CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_STATUS_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_PLUGIN_PATH: ' /usr/share/java/'
```

```text
docker-compose -f kafka-docker-compose.yml up -d
```


```yaml


version: '3.6'

volumes:
  zookeeper-data:
    driver: local
  zookeeper-log:
    driver: local
  kafka-data:
    driver: local

services:
  akhq:
    # build:
    #   context: .
    image: tchiotludo/akhq
    environment:
      AKHQ_CONFIGURATION: |
        akhq:
          connections:
            docker-kafka-server:
              properties:
                bootstrap.servers: "kafka:9092"
              schema-registry:
                url: "http://schema-registry:8085"
              connect:
                - name: "connect"
                  url: "http://connect:8083"

    ports:
      - 9000:8080
    links:
      - kafka
      - schema-registry

  zookeeper:
    image: confluentinc/cp-zookeeper
    volumes:
      - C://docker/zookeeper-data:/var/lib/zookeeper/data
      - C://docker/zookeeper-log:/var/lib/zookeeper/log
    environment:
      ZOOKEEPER_CLIENT_PORT: '2181'
      ZOOKEEPER_ADMIN_ENABLE_SERVER: 'false'

  kafka:
    image: confluentinc/cp-kafka
    volumes:
      - C://docker/kafka-data:/var/lib/kafka/data
    environment:
      KAFKA_BROKER_ID: '0'
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_NUM_PARTITIONS: '12'
      KAFKA_COMPRESSION_TYPE: 'gzip'
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: '1'
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: '1'
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: '1'
      KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT://kafka:9092'
      KAFKA_CONFLUENT_SUPPORT_METRICS_ENABLE: 'false'
      KAFKA_JMX_PORT: '9091'
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'
      KAFKA_AUTHORIZER_CLASS_NAME: 'kafka.security.authorizer.AclAuthorizer'
      KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND: 'true'
    links:
      - zookeeper

  schema-registry:
    image: confluentinc/cp-schema-registry
    depends_on:
      - kafka
    environment:
      SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: 'PLAINTEXT://kafka:9092'
      SCHEMA_REGISTRY_HOST_NAME: 'schema-registry'
      SCHEMA_REGISTRY_LISTENERS: 'http://0.0.0.0:8085'
      SCHEMA_REGISTRY_LOG4J_ROOT_LOGLEVEL: 'INFO'

  connect:
    image: confluentinc/cp-kafka-connect
    depends_on:
      - kafka
      - schema-registry
    environment:
      CONNECT_BOOTSTRAP_SERVERS: 'kafka:9092'
      CONNECT_REST_PORT: '8083'
      CONNECT_REST_LISTENERS: 'http://0.0.0.0:8083'
      CONNECT_REST_ADVERTISED_HOST_NAME: 'connect'
      CONNECT_CONFIG_STORAGE_TOPIC: '__connect-config'
      CONNECT_OFFSET_STORAGE_TOPIC: '__connect-offsets'
      CONNECT_STATUS_STORAGE_TOPIC: '__connect-status'
      CONNECT_GROUP_ID: 'kafka-connect'
      CONNECT_KEY_CONVERTER_SCHEMAS_ENABLE: 'true'
      CONNECT_KEY_CONVERTER: 'io.confluent.connect.avro.AvroConverter'
      CONNECT_KEY_CONVERTER_SCHEMA_REGISTRY_URL: 'http://schema-registry:8085'
      CONNECT_VALUE_CONVERTER_SCHEMAS_ENABLE: 'true'
      CONNECT_VALUE_CONVERTER: 'io.confluent.connect.avro.AvroConverter'
      CONNECT_VALUE_CONVERTER_SCHEMA_REGISTRY_URL: 'http://schema-registry:8085'
      CONNECT_INTERNAL_KEY_CONVERTER: 'org.apache.kafka.connect.json.JsonConverter'
      CONNECT_INTERNAL_VALUE_CONVERTER: 'org.apache.kafka.connect.json.JsonConverter'
      CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR: '1'
      CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR: '1'
      CONNECT_STATUS_STORAGE_REPLICATION_FACTOR: '1'
      CONNECT_PLUGIN_PATH: ' /usr/share/java/'

```
