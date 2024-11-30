package top.yxy.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        //1. 创建生产者，指定分组
        DefaultMQProducer producer = new DefaultMQProducer("xyx_producer");
        //2. 这是 NameServer 的地址
        producer.setNamesrvAddr("121.199.27.54:9876");
        //3. 启动生产者
        producer.start();
        //4. 创建消息并发送
        for(int i =0;i < 10;i ++) {
            Message message = new Message("topic-xyy","Friday10",("开始学习 Rocket MQ"+i).getBytes());
            SendResult sendResult = producer.send(message);
            log.info("发送的消息：{}，发送的结果：{}",message,sendResult.toString());
        }
        //5. 关闭生产者
        producer.shutdown();
    }

}
