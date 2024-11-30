package top.yxy.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import top.yxy.cloud.common.handler.FeignConfig;
import top.yxy.cloud.common.handler.SentinelConfig;
import top.yxy.cloud.common.mybatis.MyBatisConfig;
import top.yxy.cloud.common.properties.JwtProperties;
import top.yxy.cloud.common.properties.SsyProperties;

/**
 * @author yxy
 * @date 2024/9/11
 * @description UserApplication
 **/
@SpringBootApplication
@EnableFeignClients
@Import({MyBatisConfig.class, FeignConfig.class, SentinelConfig.class})
@EnableConfigurationProperties({SsyProperties.class, JwtProperties.class})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
