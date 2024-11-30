package top.yxy.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

@Slf4j
public class LogisticsConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_group");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("orderTopic", "Logistics");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs,context) -> {
            for (MessageExt msg : msgs) {
                log.info("物流处理订单消息：{}}",new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        log.info("物流消费者已启动！");
    }
}
