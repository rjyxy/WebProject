package top.yxy.rocketmq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.yxy.rocketmq.entity.Article;
import top.yxy.rocketmq.entity.Submission;
import top.yxy.rocketmq.mapper.ArticleMapper;
import top.yxy.rocketmq.mapper.SubmissionMapper;
import top.yxy.rocketmq.service.ArticleService;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
   private final ArticleMapper articleMapper;

    @Override
    public void publishArticle(Article article) {
        // 设置文章状态为待审核
        article.setStatus("PENDING");
        //插入数据库
        articleMapper.insert(article);
    }

    @Override
    public void approveArticle(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article != null) {
            article.setStatus("APPROVED");
            articleMapper.updateById(article);
        }
    }

}
