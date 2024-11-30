package top.yxy.rocketmq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yxy.rocketmq.entity.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
