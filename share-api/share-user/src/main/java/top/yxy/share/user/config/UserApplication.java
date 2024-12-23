package top.yxy.share.user.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("top.yxy")
@MapperScan("top.yxy.share.*.mapper")
@Slf4j
//@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(UserApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("启动成功！");
        log.info("测试地址: http://127.0.0.1:{}{}/hello",env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"));

    }
}
