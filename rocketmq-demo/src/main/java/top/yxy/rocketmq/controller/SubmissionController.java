package top.yxy.rocketmq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.yxy.rocketmq.entity.Article;
import top.yxy.rocketmq.entity.Submission;
import top.yxy.rocketmq.entity.UserSubmission;
import top.yxy.rocketmq.mapper.SubmissionMapper;
import top.yxy.rocketmq.mapper.UserSubmissionMapper;
import top.yxy.rocketmq.service.PointsProducer;
import top.yxy.rocketmq.service.SubmissionService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionMapper submissionMapper;
    private final SubmissionService submissionService;
    private final PointsProducer pointsProducer;
    private final UserSubmissionMapper userSubmissionMapper;

    /**
     * 返回投稿文章的 userId
     *
     * @param submissionId 提交的投稿ID
     * @return 用户ID
     */
    public Long getUserIdBySubmissionId(Long submissionId) {
        UserSubmission userSubmission = userSubmissionMapper.selectOne(new QueryWrapper<UserSubmission>().eq("submission_id", submissionId));
        return userSubmission != null ? userSubmission.getUserId() : null;
    }

    //投稿
    @PostMapping("/submit/{id}")
    public String submitArticle(@PathVariable Long id) {
        try {
            submissionService.submitArticle(id);
            Long userId = getUserIdBySubmissionId(id); // 假设这个方法存在
            if (userId != null) {
                pointsProducer.sendPointsMessage(userId, 10); // 发送增加积分的消息
            }
            return "文章投稿通过，积分奖励已发放！";
        } catch (Exception e) {
            return "投稿失败：" + e.getMessage();
        }
    }

    //我的投稿
    @GetMapping("/my-submissions/{userId}")
    public List<Submission> getMySubmissions(@PathVariable Long userId) {
        return submissionService.getMySubmissions(userId);
    }

    //查询所有待审核的文章
    @GetMapping("/admin/pending-submissions")
    public List<Submission> getPendingSubmissions() {
        return submissionService.getPendingSubmissions();
    }

    // 审核通过投稿
    @PostMapping("/approve/{id}")
    public String approveSubmission(@PathVariable Long id) {
        submissionService.approveSubmission(id);
        Long userId = getUserIdBySubmissionId(id);
        if (userId != null) {
            pointsProducer.sendPointsMessage(userId, 50);
            return "投稿审核通过，积分奖励已发放！";
        }
        return "投稿审核失败！";
    }

    // 审核拒绝投稿
    @PostMapping("/reject/{id}")
    public String rejectSubmission(@PathVariable Long id) {
        submissionService.rejectSubmission(id);
        Long userId = getUserIdBySubmissionId(id);
        if (userId != null) {
            return "投稿审核失败，请重试！";
        }
        return "投稿审核失败！";
    }
}
