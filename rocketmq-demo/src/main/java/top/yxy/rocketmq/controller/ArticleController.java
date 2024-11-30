package top.yxy.rocketmq.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.yxy.rocketmq.entity.Article;
import top.yxy.rocketmq.entity.Submission;
import top.yxy.rocketmq.mapper.ArticleMapper;
import top.yxy.rocketmq.mapper.SubmissionMapper;
import top.yxy.rocketmq.service.ArticleService;
import top.yxy.rocketmq.service.PointsProducer;

@AllArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final PointsProducer pointsProducer;
    private final ArticleMapper articleMapper;

    //发布文章接口
    @PostMapping("/publish")
    public String publishArticle(@RequestBody Article article){
        articleService.publishArticle(article);
        return"文章发布成功，等待审核！";
    }
    //审核文章接口
    @PostMapping("/approve/{id}")
    public String approveArticle(@PathVariable Long id){
        articleService.approveArticle(id);
        //发送消息，加积分50
        Article article=articleMapper.selectById(id);
        pointsProducer.sendPointsMessage(article.getUserId(),50);
        return "文章审核通过，积分奖励已发放！";
    }

}
