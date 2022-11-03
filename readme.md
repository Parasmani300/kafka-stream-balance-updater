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
kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic word-count-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


--------------
Run:
kafka-stream-start-0.0.1-SNAPSHOT.jar

kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic word-count-input --create --partitions 3 --replication-factor 1
kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic word-count-output --create --partitions 3 --replication-factor 1




### Avro formatter from json:
https://toolslick.com/text/formatter/json
#Link 2

https://toolslick.com/generation/metadata/avro-schema-from-json

#JSON Serializer
https://github.com/apache/kafka/blob/2.1/streams/examples/src/main/java/org/apache/kafka/streams/examples/pageview/PageViewTypedDemo.java#L83
