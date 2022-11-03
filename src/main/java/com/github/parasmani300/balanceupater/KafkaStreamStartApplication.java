package com.github.parasmani300.balanceupater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

@SpringBootApplication
public class KafkaStreamStartApplication {

	public Topology createTopology(){
		StreamsBuilder builder = new StreamsBuilder();

		final Serializer<JsonNode> jsonNodeSerializer = new JsonSerializer();
		final Deserializer<JsonNode> jsonNodeDeserializer = new JsonDeserializer();

		final Serde<JsonNode> jsonNodeSerde = Serdes.serdeFrom(jsonNodeSerializer,jsonNodeDeserializer);

		KStream<String,JsonNode> bankTransitions = builder
				.stream("sample-balance",Consumed.with(Serdes.String(),jsonNodeSerde));

		// create the initial json object for balances
		ObjectNode initialBalance = JsonNodeFactory.instance.objectNode();
		initialBalance.put("count", 0);
		initialBalance.put("balance", 0);
		initialBalance.put("time", "");

		KTable<String,JsonNode> bankBalance = bankTransitions
				.groupByKey(Grouped.with(Serdes.String(),jsonNodeSerde))
				.aggregate(
						() -> initialBalance,
						(key, transaction, balance) -> newBalance(transaction, balance),
						Materialized.<String, JsonNode, KeyValueStore<Bytes, byte[]>>as("bank-balance-agg")
								.withKeySerde(Serdes.String())
								.withValueSerde(jsonNodeSerde)
				);

		bankBalance.toStream().to("publish-once",Produced.with(Serdes.String(),jsonNodeSerde));
		System.out.println("Hello World");
		return builder.build();
	}


	private static JsonNode newBalance(JsonNode transaction, JsonNode balance) {
		// create a new balance json object
		ObjectNode newBalance = JsonNodeFactory.instance.objectNode();
		newBalance.put("count", balance.get("count").asInt() + 1);
		newBalance.put("balance", balance.get("balance").asInt() + transaction.get("balance").asInt());
//		newBal
//		Long balanceEpoch = Instant.parse(balance.get("time").asText()).toEpochMilli();
//		Long transactionEpoch = Instant.parse(transaction.get("time").asText()).toEpochMilli();
//		Instant newBalanceInstant = Instant.ofEpochMilli(Math.max(balanceEpoch, transactionEpoch));
		newBalance.put("time", "");
		return newBalance;
	}


	public static void main(String[] args) {

		SpringApplication.run(KafkaStreamStartApplication.class, args);

		Properties config = new Properties();
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, "balance-application");
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		// we disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
		config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

		// Exactly once processing!!
//		config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);


		KafkaStreamStartApplication kafkaStreamStartApplication = new KafkaStreamStartApplication();

		KafkaStreams kafkaStreams = new KafkaStreams(kafkaStreamStartApplication.createTopology(),config);
		kafkaStreams.start();

		System.out.println(kafkaStreams.toString());

		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));



	}

}
