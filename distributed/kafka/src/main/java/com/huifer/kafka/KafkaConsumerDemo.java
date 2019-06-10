package com.huifer.kafka;

import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * <p>Title : KafkaConsumerDemo </p>
 * <p>Description : </p>
 *
 * @author huifer
 * @date 2019-06-10
 */
public class KafkaConsumerDemo extends Thread {

    private final KafkaConsumer kafkaConsumer;
    private final String topic;
    public KafkaConsumerDemo(String topic) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.57.1:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerDemo-java");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        this.topic = topic;
    }

    public static void main(String[] args) {
        new KafkaConsumerDemo("test").start();
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Integer, String> poll = kafkaConsumer.poll(1000);
            for (ConsumerRecord<Integer, String> record : poll) {
                System.out.println("接收消息：" + record.value());
            }

        }
    }

}
