package top.yxy.rocketmq.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article")
public class Article {
    @TableId
    private Long id;
    //发布文章的用户ID
    private Long userId;
    //文章标题
    private String title;
    //文章内容
    private String content;
    //审核状态： PENDing / APPROVED
    private String status;
}
