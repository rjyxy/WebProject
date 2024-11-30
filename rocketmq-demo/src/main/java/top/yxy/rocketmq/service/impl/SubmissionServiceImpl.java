package top.yxy.rocketmq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yxy.rocketmq.entity.Submission;
import top.yxy.rocketmq.entity.UserSubmission;
import top.yxy.rocketmq.mapper.SubmissionMapper;
import top.yxy.rocketmq.mapper.UserSubmissionMapper;
import top.yxy.rocketmq.service.SubmissionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionMapper submissionMapper;
    private final UserSubmissionMapper userSubmissionMapper;

    @Override
    public void submitArticle(Long submissionId) {
        //默认审核状态未未提交，数据库表已操作
        //根据 ID 来判断该段数据是否出为空
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", submissionId);
        Submission submission = submissionMapper.selectOne(queryWrapper);
        if (submission != null) {
            submission.setStatus("PENDING");
            submissionMapper.updateById(submission);
        }
    }

    @Override
    public List<Submission> getMySubmissions(Long userId) {
        List<UserSubmission> userSubmissions = userSubmissionMapper.selectList(new QueryWrapper<UserSubmission>().eq("user_id", userId));
        return userSubmissions.stream()
                .map(userSubmission -> submissionMapper.selectById(userSubmission.getSubmissionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Submission> getPendingSubmissions() {
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "PENDING");
        return submissionMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void approveSubmission(Long submissionId) {
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", submissionId);
        Submission submission = submissionMapper.selectOne(queryWrapper);
        if (submission != null) {
            submission.setStatus("APPROVED");
            submissionMapper.updateById(submission);
        }
    }

    @Override
    @Transactional
    public void rejectSubmission(Long submissionId) {
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", submissionId);
        Submission submission = submissionMapper.selectOne(queryWrapper);
        if (submission != null) {
            submission.setStatus("REJECTED");
            submissionMapper.updateById(submission);
        }
    }
}
