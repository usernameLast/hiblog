package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.Article;
import cc.lastone.hiblog.to.NewestArticleTo;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.vo.ArticleDetailVo;
import cc.lastone.hiblog.vo.ArticleSetVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ArticleService {
    // 获得修改博客时的数据
    ArticleSetVo getArticleSetInfo(Integer uid, Integer id);

    // 修改博客的所有内容
    ResultData setArticleInfo(ArticleSetVo articleSetVo, Integer uid);

    // 查询博客
    Map searchArticle(
            String startDate,
            String endDate,
            int type,
            int status,
            int is_private,
            String title,
            Integer uid,
            int categoryId,
            Pageable pageable
    );

    // 查询博客
    Map searchArticle(String title, Pageable pageable);

    // 删除博客
    ResultData deleteMyArtilce(Integer id, Integer uid);

    ResultData editArticle(Article article, Integer uid);

    ArticleDetailVo getArticleShowInfo(Integer id, Integer currentUid);

    List<NewestArticleTo> getNewstArticleList(Integer uid, Integer size);

    ResultData collectArticle(Integer uid, Integer articleId);

    ResultData cancelCollectArticle(Integer uid, Integer articleId);

    Map getCollectArticle(Integer uid, Pageable pageable);

    // 获得首页博客
    Map getIndexArticle(int categoryId, Pageable pageable);

    // 获得热门博客
    List<Article> getPopularArticle(Integer size);
}
