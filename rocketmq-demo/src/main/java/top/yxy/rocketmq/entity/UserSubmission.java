package top.yxy.rocketmq.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_submission")
public class UserSubmission {
    @TableId
    private Long submissionId;
    // 用户ID
    private Long userId;
}
