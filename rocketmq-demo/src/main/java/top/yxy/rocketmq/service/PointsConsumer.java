package top.yxy.rocketmq.service;

import lombok.AllArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import top.yxy.rocketmq.entity.PointsLog;
import top.yxy.rocketmq.mapper.PointsLogMapper;

@Service
@RocketMQMessageListener(topic = "PointsTopic_xyy",selectorExpression = "ArticleApproved_xyy",consumerGroup = "points_group_xyy")
@AllArgsConstructor
public class PointsConsumer implements RocketMQListener<String> {
    private final UserService userService;
    private final PointsLogMapper pointsLogMapper;

    @Override
    public void onMessage(String message) {
        String[] parts=message.split(":");
        Long userId=Long.parseLong(parts[0]);
        int points=Integer.parseInt(parts[1]);
        // 1. 给用户加积分
        userService.addPoints(userId,points);
        // 2. 记录积分日志
        PointsLog pointsLog=new PointsLog();
        pointsLog.setUserId(userId);
        pointsLog.setPoints(points);
        pointsLogMapper.insert(pointsLog);
    }
}
