package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.ArticleCategory;
import cc.lastone.hiblog.domain.ArticleCategoryPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, ArticleCategoryPrimaryKey> {

    //    @Query(value="from ArticleCategory where article_id = ?1")
    @Query(value = "select ac from ArticleCategory ac where ac.articleId = ?1")
    List<ArticleCategory> findAllByArticleId(Integer article_id);

//    @Modifying
//    @Query("delete from ArticleCategory where article_id = ?1")
//    int deleteByArticle_id(Integer article_id);

    @Modifying
    int deleteByArticleId(Integer article_id);

    int countByCategoryId(Integer category_id);

    @Query(
            nativeQuery = true,
            value = "SELECT count(hac.article_id) " +
                    "from hb_article_category hac " +
                    "inner join hb_article ha on hac.article_id = ha.id  " +
                    "where hac.category_id = ?1 " +
                    "and ha.is_private = 0 " +
                    "and ha.status = 2 "
    )
    BigInteger countCategoryId(Integer category_id);


}
