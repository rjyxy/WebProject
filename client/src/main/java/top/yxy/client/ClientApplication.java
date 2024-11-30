package top.yxy.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class ClientApplication {
    private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void task() {
        logger.info("正在执行定时任务---");
    }

}
