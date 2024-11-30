package top.yxy.rocketmq.service;

import top.yxy.rocketmq.entity.Article;

public interface ArticleService {
    void publishArticle(Article article);//发布文章
    void approveArticle(Long articleId);//审核文章
}
