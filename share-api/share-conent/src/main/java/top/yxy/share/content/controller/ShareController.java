package top.yxy.share.content.controller;

import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.yxy.share.common.util.JwtUtil;
import top.yxy.share.content.domain.dto.ExchangeDTO;
import top.yxy.share.content.domain.dto.ShareRequestDTO;
import top.yxy.share.content.domain.entity.Notice;
import top.yxy.share.content.domain.entity.Share;
import top.yxy.share.content.domain.resp.ShareResp;
import top.yxy.share.content.service.NoticeService;
import top.yxy.share.common.resp.CommonResp;
import top.yxy.share.content.service.ShareService;

import java.util.List;

@RestController
@RequestMapping("/share")
@Slf4j
public class ShareController {
    @Resource
    private NoticeService noticeService;

    @Resource
    private ShareService shareService;

    //定义每页最多的数据条数，以防前端传递超大参数，造成页面数据量最大
    private final int MAX = 50;

    /*编写获取最新公告的接口*/
    @GetMapping("/notice")
    public CommonResp<Notice> getLatesNotice() {
        CommonResp<Notice> commonResp = new CommonResp<>();
        commonResp.setData(noticeService.getLatest());
        return commonResp;
    }

    /*查询数据，先定死一个用户（后面再加上从 token 中解析userId +分⻚*/
    @GetMapping("/list")
    public CommonResp<List<Share>> getShareList(@RequestParam(required = false) String title,
                                                @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                                @RequestParam(required = false,defaultValue = "3") Integer pageSize,
                                                @RequestHeader(value = "token", required = false) String token) {
        if (pageSize > MAX) {
            pageSize = MAX;
        }
        Long userId = getUserIdFromToken(token);
        CommonResp<List<Share>> commonResp = new CommonResp<>();
        commonResp.setData(shareService.getList(title,pageNo,pageSize,userId));
        return commonResp;
    }

    /**
     * 封装一个私有方法，从 token 中解析出 userId
     *
     * @param  token token
     * @return userId
     * */
    private Long getUserIdFromToken(String token) {
        long userId = 0;
        String noToken = "no-token";
        if (!noToken.equals(token)) {
            JSONObject jsonObject= JwtUtil.getJSONObject(token);
            log.info("解析到 token 中的数据：{}",jsonObject);
            userId=Long.parseLong(jsonObject.get("id").toString());
        } else {
            log.info("没有 token");
        }
        return userId;
    }

//    新增根据id 查询分享内容的接口
    @GetMapping("/{id}")
    public CommonResp<ShareResp> getShareById(@PathVariable Long id) {
        ShareResp shareResp = shareService.findById(id);
        CommonResp<ShareResp> commonResp = new CommonResp<>();
        commonResp.setData(shareResp);
        return commonResp;
    }

    //新增兑换接口
    @PostMapping("/exchange")
    public CommonResp<Share> exchangeShare(@RequestBody ExchangeDTO exchangeDTO) {
        CommonResp<Share> resp = new CommonResp<>();
        resp.setData(shareService.exchange(exchangeDTO));
        return resp;
    }

    @PostMapping("/contribute")
    public CommonResp<Integer> contribute(@RequestBody ShareRequestDTO shareRequestDTO, @RequestHeader(value="token",required=false)String token){
        Long userId=getUserIdFromToken(token);
        shareRequestDTO.setUserId(userId);
        CommonResp<Integer> resp=new  CommonResp<>();
        resp.setData(shareService.contribute(shareRequestDTO));
        return resp;
    }

    @GetMapping("/myContribute")
    public CommonResp<List<Share>> myContribute(
            @RequestParam(required=false,defaultValue="1") Integer pageNo,
            @RequestParam(required=false,defaultValue="3") Integer pageSize,
            @RequestHeader(value="token",required=false) String token){
        if(pageSize>MAX){
            pageSize=MAX;
        }
    Long userId=getUserIdFromToken(token);
    CommonResp<List<Share>> resp=new CommonResp<>();
    resp.setData(shareService.myContribute(pageNo,pageSize,userId));
    return resp;
    }
}
