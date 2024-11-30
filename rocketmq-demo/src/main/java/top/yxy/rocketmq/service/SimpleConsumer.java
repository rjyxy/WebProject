package top.yxy.rocketmq.service;

import apache.rocketmq.v2.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class SimpleConsumer {
    public static void main(String[] args) throws Exception {
        //1. 创建消费者，并指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("simple_consumer");
        //2. 设置 NameServer 地址
        consumer.setNamesrvAddr("121.199.27.54:9876");
        //3. 订阅 Topic 和 Tag
        consumer.subscribe("topic-xyy","Friday10");
        //4. 注册消息的监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    log.info("接收到消息： {}",new String(messageExt.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5. 启动消费者
        consumer.start();
        log.info("消费者已启动！");
    }
}
