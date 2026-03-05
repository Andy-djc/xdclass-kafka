package net.xdclass.xdclasskafka;

import java.util.Properties;

/**
 * ClassName: KafkaProducerTest
 * Package: net.xdclass.xdclasskafka
 * Description:
 *
 * @Author 丁进超
 * @Create 2026/1/9 16:18
 * @Version 2024.1.7
 */
public class KafkaProducerTest {
    public static Properties getProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.254.128:9092");
        props.put("acks","all");
        props.put("retries",0);
        props.put("batch.size",16384);
        props.put("linger.ms",5);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
