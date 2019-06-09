package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.ArticleCollect;
import cc.lastone.hiblog.domain.ArticleCollectPrimaryKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleCollectRepository extends JpaRepository<ArticleCollect, ArticleCollectPrimaryKey> {

    int countByUidAndArticleId(Integer uid, Integer articleId);

    @Modifying
    int deleteByUidAndArticleId(Integer uid, Integer articleId);

    // 查询收藏博客 使用 Pageable 分页
    @Query(nativeQuery = true,
            value = "select  ha.id,ha.uid,ha.title,hu.nickname " +
                    "from hb_article_collect hac " +
                    "inner join hb_article ha on hac.article_id = ha.id " +
                    "inner join hb_user hu on hu.id = ha.uid " +
                    "where hac.uid = :uid",
            countQuery = "select  count(ha.id) " +
                    "from hb_article_collect hac " +
                    "inner join hb_article ha on hac.article_id = ha.id " +
                    "inner join hb_user hu on hu.id = ha.uid " +
                    "where hac.uid = :uid"
    )
    Page<Object[]> getCollectArticle(
            @Param("uid") Integer uid,
            Pageable pageable
    );

}
