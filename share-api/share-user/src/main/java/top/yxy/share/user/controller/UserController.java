package top.yxy.share.user.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yxy.share.common.resp.CommonResp;
import top.yxy.share.user.domain.dto.LoginDTO;
import top.yxy.share.user.domain.dto.UserAddBonusMsgDTO;
import top.yxy.share.user.domain.entity.User;
import top.yxy.share.user.domain.resp.UserLoginResp;
import top.yxy.share.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/count")
    public Long count() {
        return userService.count();
    }

    // 登录接口
    @PostMapping("/login")
    // 封装统⼀返回结果
    //中使用 Valid 注解对请求参数进行校验
    public CommonResp<UserLoginResp> login(@Valid @RequestBody LoginDTO loginDTO) {
        UserLoginResp userLoginResp = userService.login(loginDTO);
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        resp.setData(userLoginResp);
        return resp;
    }

    //注册接口
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
        Long id = userService.register(loginDTO);
        CommonResp<Long> resp = new CommonResp<>();
        resp.setData(id);
        return resp;
    }

    //根据 id 来查找用户
    @GetMapping("/{id}")
    public CommonResp<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        CommonResp<User> resp = new CommonResp<>();
        resp.setData(user);
        return resp;
    }

    //新增更新用户积分接口
    @PostMapping("/updateBonus")
    public CommonResp<User> updateBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO) {
        Long userId = userAddBonusMsgDTO.getUserId();
        userService.updateBonus(
                UserAddBonusMsgDTO.builder()
                        .userId(userId)
                        .bonus(userAddBonusMsgDTO.getBonus())
                        .description("兑换分享内容")
                        .event("BUY")
                        .build()
        );
        CommonResp<User> resp = new CommonResp<>();
        resp.setData(userService.findById(userId));
        return resp;
    }
}