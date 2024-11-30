package top.yxy.rocketmq.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.yxy.rocketmq.entity.User;
import top.yxy.rocketmq.mapper.UserMapper;
import top.yxy.rocketmq.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public void addPoints(Long userId, int points) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPoints(user.getPoints() + points);
            userMapper.updateById(user);
        }
    }
}
