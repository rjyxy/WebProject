package top.yxy.cloud.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import top.yxy.cloud.common.handler.FeignConfig;
import top.yxy.cloud.common.handler.SentinelConfig;
import top.yxy.cloud.common.mybatis.MyBatisConfig;
import top.yxy.cloud.common.properties.SsyProperties;

/**
 * @author mqxu
 * @date 2024/9/11
 * @description ProductApplication
 **/
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({SsyProperties.class})
@Import({MyBatisConfig.class, FeignConfig.class, SentinelConfig.class})
@MapperScan(basePackages = {"top.yxy.cloud.product.mapper"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}