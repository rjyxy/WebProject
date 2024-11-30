package top.yxy.cloud.common.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class SentinelConfig {
    @Bean
    public BlockExceptionHandler blockExceptionHandler() {
        return new SentinelExceptionHandler();
    }

    @Bean
    public RequestOriginParser requestOriginParser() {
        return new SentinelRequestOriginParser();
    }


}
