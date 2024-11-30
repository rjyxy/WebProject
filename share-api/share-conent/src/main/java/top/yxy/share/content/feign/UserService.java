package top.yxy.share.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.yxy.share.common.resp.CommonResp;
import top.yxy.share.content.domain.dto.UserAddBonusMsgDTO;
import top.yxy.share.content.domain.entity.User;

@FeignClient(value = "user-service",path = "/user")
public interface UserService {

    @GetMapping("/{id}")
    CommonResp<User> getUser(@PathVariable Long id);

/**
 *  调用用户服务的修改用户积分接口
 *  @param userAddBonusMsgDTO 积分信息
 * @return CommonResp<User>
 **/
    @PostMapping("/updateBonus")
    CommonResp<User> updateBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO);

}
