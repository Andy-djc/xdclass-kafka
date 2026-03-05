package net.xdclass.xdclasskafka;

import org.apache.kafka.clients.admin.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * ClassName: KafkaAdminTest
 * Package: net.xdclass.xdclasskafka
 * Description:
 *
 * @Author 丁进超
 * @Create 2026/1/9 14:58
 * @Version 2024.1.7
 */
//@SpringBootTest
public class KafkaAdminTest {

    private static final String TOPIC_NAME = "andy-topic";

    public static AdminClient initAdminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.254.128:9092");

        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }

    //创建主题
    @Test
    public void createTopicTest(){
        AdminClient adminClient = initAdminClient();

        //指定分区数量,副本数量
        NewTopic newTopic = new NewTopic(TOPIC_NAME, 2, (short) 1);

        //
        CreateTopicsResult createTopicsResult = adminClient.createTopics(Arrays.asList(newTopic));

        try {
            //future等待创建,成功则不会有任何报错,否则会抛出异常
            createTopicsResult.all().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //列举主题
    @Test
    public void listTopicTest() throws ExecutionException, InterruptedException {
        AdminClient adminClient = initAdminClient();

        //是否查看内部的topic,默认false
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);

        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        Set<String> topics = listTopicsResult.names().get();
        for (String name : topics) {
            System.err.println(name);
        }
    }

    //删除主题
    @Test
    public void delTopicTest() throws ExecutionException, InterruptedException {
        AdminClient adminClient = initAdminClient();
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList(TOPIC_NAME));
        deleteTopicsResult.all().get();
    }

    //查看主题详情
    @Test
    public void detailTopicTest() throws ExecutionException, InterruptedException {
        AdminClient adminClient = initAdminClient();
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Arrays.asList("t1"));
        Map<String, TopicDescription> stringTopicDescriptionMap = describeTopicsResult.all().get();
        Set<Map.Entry<String, TopicDescription>> entries = stringTopicDescriptionMap.entrySet();
        for (Map.Entry<String, TopicDescription> entry : entries) {
            System.err.println("name: "+entry.getKey()+",desc: "+entry.getValue());
        }
    }

    //增加topic分区数量
    @Test
    public void incrPartitionTopicTest() throws ExecutionException, InterruptedException {
        Map<String, NewPartitions> infoMap = new HashMap<>();

        AdminClient adminClient = initAdminClient();
        NewPartitions newPartitions = NewPartitions.increaseTo(5);

        infoMap.put("t1",newPartitions);
        CreatePartitionsResult createPartitionsResult = adminClient.createPartitions(infoMap);
        createPartitionsResult.all().get();

    }
}
