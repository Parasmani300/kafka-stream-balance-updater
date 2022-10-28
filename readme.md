kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic word-count-input --create --partitions 3 --replication-factor 1
kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic word-count-output --create --partitions 3 --replication-factor 1

kafka-topics.bat --zookeeper 127.0.0.1:2181 --list

-----------------------
CONSOLE PRODUCER
------------------------
kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic word-count-input

--------------------
CONSOLE CONSUMER with some extra info
--------------------
kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 \
 --topic word-count-output \
--from-beginning \
--formatter kafka.tools.DefaultKeyFormatter \
--property print.key=true \
--property print.value=true \
--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
--propertyvalue.deserializer=org.apache.kafka.common.serialization.LongDeserializer


### Avro formatter from json:
https://toolslick.com/text/formatter/json
