package top.yxy.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yxy.share.common.resp.CommonResp;
import top.yxy.share.content.domain.dto.ExchangeDTO;
import top.yxy.share.content.domain.dto.ShareRequestDTO;
import top.yxy.share.content.domain.dto.UserAddBonusMsgDTO;
import top.yxy.share.content.domain.entity.MidUserShare;
import top.yxy.share.content.domain.entity.Share;
import top.yxy.share.content.domain.entity.User;
import top.yxy.share.content.domain.resp.ShareResp;
import top.yxy.share.content.feign.UserService;
import top.yxy.share.content.mapper.MidUserShareMapper;
import top.yxy.share.content.mapper.ShareMapper;

import java.util.Date;
import java.util.List;

@Service
public class ShareService {
    @Resource
    private ShareMapper shareMapper;

    @Resource
    private UserService userService;

    @Resource
    private MidUserShareMapper midUserShareMapper;

    public List<Share> getList(String title,Integer pageNo,Integer pageSize,Long userId){
        //构造查询条件
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        //按照 id 降序查询所有数据
        wrapper.orderByDesc(Share::getId);
        //如果标题的关键字不空，则加上模糊查询条件过滤查询结果，否则结果就是所有数据
        if (title != null) {
            wrapper.like(Share::getTitle, title);
        }
        //过滤出所有已经通过审核并显示属性为 true 的数据
        wrapper.eq(Share::getAuditStatus,"PASS").eq(Share::getShowFlag,true);

        //内置的分页对象
        Page<Share> page = Page.of(pageNo, pageSize);
        //按条件查询
        List<Share> shares = shareMapper.selectList(page,wrapper);

        //处理后的 Share 数据列表
        List<Share> sharesDeal;
        // 1. 如果用户没有登录，那么 downloadUrl 全部设为 nul
        if (userId != null) {
            sharesDeal = shares.stream().peek(share ->
                    share.setDownloadUrl(null)).toList();
        }
        //2. 如果用户登录了，name查询 mid_user_share 表，如果没有数据，
        // 那么这条 share 的 downloadUrl 也设为 null
        // 只有自己分享的资源才能直接看到下载链接，否则显示“兑换
        else {
            sharesDeal = shares.stream().peek(share -> {
                MidUserShare midUserShare = midUserShareMapper.selectOne(
                        new QueryWrapper<MidUserShare>().lambda()
                                .eq(MidUserShare::getUserId,userId)
                                .eq(MidUserShare::getShareId,share.getId())
                );
                if (midUserShare == null) {
                    share.setDownloadUrl(null);
                }
            }).toList();
        }
        return sharesDeal;
    }

    public ShareResp findById(Long shareId) {
        Share share = shareMapper.selectById(shareId);
        //调用 feign 方法，根据用户 id 查询到用户信息
        CommonResp<User> commonResp = userService.getUser(share.getUserId());
        return ShareResp.builder()
                .share(share)
                .nickname(commonResp.getData().getNickname())
                .avatarUrl(commonResp.getData().getAvatarUrl())
                .build();
    }

    public Share exchange(ExchangeDTO exchangeDTO) {
        Long userId=exchangeDTO.getUserId();
        Long shareId=exchangeDTO.getShareId();
        //1. 根据 id 查询 share，校验需要兑换的内容是否存在
        Share share=shareMapper.selectById(shareId);
        if(share==null){
            throw new IllegalArgumentException("该分享内容不存在！");
        }
        //2.如果当前用户已经兑换过，则直接返回该分享内容（不需要扣积分）
        MidUserShare midUserShare=midUserShareMapper.selectOne(
                new QueryWrapper<MidUserShare>().lambda().
                        eq(MidUserShare::getUserId,userId)
                        .eq(MidUserShare::getShareId,shareId)
        );
        if(midUserShare!=null){
            return share;
        }
        //3.判断用户积分是否足够兑换该内容
        CommonResp<User> commonResp=userService.getUser(userId);
        User user=commonResp.getData();
        //兑换这条内容需要的积分
        Integer price=share.getPrice();
        //用户的积分不够
        if(price>user.getBonus()){
            throw new IllegalArgumentException("用户积分不够！");
        }
        // 4. 修改积分：乘以-1变成负值，就是扣分
        userService.updateBonus(UserAddBonusMsgDTO.builder().userId(userId).bonus(price*-1).build());
        //5. 向 mid_user_share 表插入一条数据，记录这个用户已经兑换过，就拥有了下载权限（前端⻚面）
        midUserShareMapper.insert(MidUserShare.builder().userId(userId).shareId(shareId).build());
        return share;
    }

    /**
     * 投稿
     * @param shareRequestDTO 投稿参数
     * @return int
     **/
    public int contribute(ShareRequestDTO shareRequestDTO){
        Share share=Share.builder()
                .isOriginal(shareRequestDTO.getIsOriginal())
                .author(shareRequestDTO.getAuthor())
                .price(shareRequestDTO.getPrice())
                .downloadUrl(shareRequestDTO.getDownloadUrl())
                .summary(shareRequestDTO.getSummary())
                .buyCount(0).title(shareRequestDTO.getTitle())
                .userId(shareRequestDTO.getUserId())
                .cover(shareRequestDTO.getCover())
                .showFlag(false).auditStatus("NOT_YET")
                .reason("未审核")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        return shareMapper.insert(share);
    }

    public List<Share> myContribute(Integer pageNo,Integer pageSize,Long userId){
        LambdaQueryWrapper<Share> wrapper=new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        wrapper.eq(Share::getUserId,userId);
        Page<Share> page=Page.of(pageNo,pageSize);
        return shareMapper.selectList(page,wrapper);
    }

    /**
     * 查询待审核的shares列表
     * @return List<Share>
     **/
    public List<Share> queryShareNotYet(){
        LambdaQueryWrapper<Share> wrapper=new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        wrapper.eq(Share::getAuditStatus,"NOT_YET").eq(Share::getShowFlag,false);
        return shareMapper.selectList(wrapper);
    }
}
