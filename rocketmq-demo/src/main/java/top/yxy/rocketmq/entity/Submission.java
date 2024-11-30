package top.yxy.rocketmq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("submission")
public class Submission {
    @TableId
    private Long id;
    //标题
    private String title;
    //作者
    private String author;
    //简介
    private String description;
    //封面图链接
    private String coverImageUrl;
    // 下载地址
    private String downloadUrl;
    // 价格
    private BigDecimal price;
    // 原创或转载标识（1：原创，0：转载）
    private Integer isOriginal;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
    // 投稿状态（unsubmitted:未投稿 pending：待审核，approved：审核通过，rejected：审核拒绝）
    private String status;
}
