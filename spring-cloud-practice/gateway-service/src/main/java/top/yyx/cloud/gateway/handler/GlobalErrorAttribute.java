package top.yyx.cloud.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class GlobalErrorAttribute extends DefaultErrorAttributes {
    @Override
    public Map<String,Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        log.error("网关处理异常",error);
        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put("code", HttpStatus.BAD_REQUEST.value());
        errorMap.put("msg",error.getMessage());
        return errorMap;
    }
}
