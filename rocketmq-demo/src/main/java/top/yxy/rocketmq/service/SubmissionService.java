package top.yxy.rocketmq.service;

import top.yxy.rocketmq.entity.Submission;

import java.util.List;

public interface SubmissionService {
    void submitArticle(Long submissionId);//投稿文章
    List<Submission> getMySubmissions(Long userId);//根据ID获取文章
    List<Submission> getPendingSubmissions();//所有待审核的文章
    void approveSubmission(Long submissionId);//审核通过
    void rejectSubmission(Long submissionId);//审核不通过
}
