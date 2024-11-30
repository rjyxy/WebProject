package top.yxy.cloud.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.yxy.cloud.api.facade.LoginFacade;
import top.yxy.cloud.api.pojo.query.LoginQuery;
import top.yxy.cloud.common.pojo.ResponseData;
import top.yxy.cloud.common.properties.JwtProperties;
import top.yxy.cloud.common.util.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yxy
 * @date 2024/9/20
 * @description LoginController
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController implements LoginFacade {

    private final JwtProperties jwtProperties;

    @Override
    public ResponseData<String> login(@RequestBody LoginQuery loginQuery) {
        log.info("用户开始登录：[{}]", loginQuery);
        // 查询数据库检查用户名密码（略）
        // 从配置中心获取安全密钥
        String secretKey = jwtProperties.getSecretKey();
        // JWT Token 附加的用户信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", "10001");
        claims.put("username", loginQuery.getUsername());
        // JWT Token 过期时间
        Date expireDate = DateUtils.addHours(new Date(), 24);
        // 生成 Token 并返回
        String token = JwtUtils.createToken(secretKey, null, claims, expireDate);
        if (StringUtils.isNotBlank(token)) {
            return ResponseData.succeed(token);
        } else {
            return ResponseData.failed();
        }
    }

}
