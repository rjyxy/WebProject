package top.yxy.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class OrderProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("order_group");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        String[] tags = new String[]{"Pay", "Logistics", "CustomerService"};
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("OrderTopic",tags[i],("Order Message: " + tags[i]).getBytes());
            SendResult sendResult = producer.send(msg);
            log.info("发送结果：{} ", sendResult);
        }
        producer.shutdown();
    }
}
