package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Article findByIdAndUid(Integer id, Integer uid);

    Optional<Article> findById(Integer id);

    // 查询没有自定义标签的博客数量
//    @Query(nativeQuery = true,
//            value = "select count(ha.id) " +
//                    "from hb_article ha " +
//                    "where " +
//                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
//                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
//                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
//                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
//                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
//                    "if (:uid > 0,ha.uid = :uid,1=1)")
//    Integer searchArticleWithoutCategoryCount(
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate,
//            @Param("type") int type,
//            @Param("status") int status,
//            @Param("title") String title,
//            @Param("uid") Integer uid
//    );

    // 查询没有自定义标签的博客
//    @Query(nativeQuery = true,
//            value = "select ha.id,ha.uid,ha.title,ha.introduction,ha.type,ha.category_id,ha.is_private,ha.visited_num,ha.comment_num,ha.created_at,ha.status " +
//                    "from hb_article ha " +
//                    "where " +
//                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
//                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
//                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
//                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
//                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
//                    "if (:uid > 0,ha.uid = :uid,1=1) " +
//                    "order by ha.top_timestamp,ha.id desc limit :start,:size")
//    List<Object[]> searchArticleWithoutCategory(
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate,
//            @Param("type") int type,
//            @Param("status") int status,
//            @Param("title") String title,
//            @Param("uid") Integer uid,
//            @Param("start") Integer start,
//            @Param("size") Integer size
//    );

    // 查询没有自定义标签的博客 使用Pageable分页
    @Query(nativeQuery = true,
            value = "select ha.id,ha.uid,ha.title,ha.introduction,ha.type,ha.category_id,ha.is_private,ha.visited_num,ha.comment_num,ha.top_timestamp,ha.created_at,ha.status " +
                    "from hb_article ha " +
                    "where " +
                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
                    "if (:is_private > -1, ha.is_private = :is_private, 1 = 1) and " +
                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
                    "if (:uid > 0,ha.uid = :uid,1=1)",
            countQuery = "select count(ha.id) " +
                    "from hb_article ha " +
                    "where " +
                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
                    "if (:is_private > -1, ha.is_private = :is_private, 1 = 1) and " +
                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
                    "if (:uid > 0,ha.uid = :uid,1=1)"
    )
    Page<Object[]> searchArticleWithoutCategoryPaging(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("type") int type,
            @Param("status") int status,
            @Param("is_private") int is_private,
            @Param("title") String title,
            @Param("uid") Integer uid,
            Pageable pageable
    );

    // 查询有自定义标签的博客数量
//    @Query(nativeQuery = true,
//            value = "select count(ha.id) " +
//                    "from hb_article ha " +
//                    "inner join hb_article_category hac on hac.article_id = ha.id " +
//                    "where " +
//                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
//                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
//                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
//                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
//                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
//                    "if (:uid > 0,ha.uid = :uid,1=1) and " +
//                    "hac.category_id = :category_id " +
//                    "group by ha.id "
//    )
//    Integer searchArticleWithCategoryCount(
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate,
//            @Param("type") int type,
//            @Param("status") int status,
//            @Param("title") String title,
//            @Param("uid") Integer uid,
//            @Param("category_id") int categoryId
//    );

    // 查询有自定义标签的博客
//    @Query(nativeQuery = true,
//            value = "select ha.id,ha.uid,ha.title,ha.introduction,ha.type,ha.category_id,ha.is_private,ha.visited_num,ha.comment_num,ha.created_at,ha.status " +
//                    "from hb_article ha " +
//                    "inner join hb_article_category hac on hac.article_id = ha.id " +
//                    "where " +
//                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
//                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
//                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
//                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
//                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
//                    "if (:uid > 0,ha.uid = :uid,1=1) and " +
//                    "hac.category_id = :category_id " +
//                    "group by ha.id " +
//                    "order by ha.top_timestamp,ha.id desc limit :start,:size"
//    )
//    List<Object[]> searchArticleWithCategory(
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate,
//            @Param("type") int type,
//            @Param("status") int status,
//            @Param("title") String title,
//            @Param("uid") Integer uid,
//            @Param("category_id") int categoryId,
//            @Param("start") Integer start,
//            @Param("size") Integer size
//    );

    // 查询有自定义标签的博客 使用 Pageable 分页
    @Query(nativeQuery = true,
            value = "select ha.id,ha.uid,ha.title,ha.introduction,ha.type,ha.category_id,ha.is_private,ha.visited_num,ha.comment_num,ha.top_timestamp,ha.created_at,ha.status " +
                    "from hb_article ha " +
                    "inner join hb_article_category hac on hac.article_id = ha.id " +
                    "where " +
                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
                    "if (:is_private > -1, ha.is_private = :is_private, 1 = 1) and " +
                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
                    "if (:uid > 0,ha.uid = :uid,1=1) and " +
                    "hac.category_id = :category_id " +
                    "group by ha.id ",
            countQuery = "select count(ha.id) " +
                    "from hb_article ha " +
                    "inner join hb_article_category hac on hac.article_id = ha.id " +
                    "where " +
                    "if (:startDate is not null,ha.created_at >= :startDate, 1 = 1) and " +
                    "if (:endDate is not null,ha.created_at <= :endDate, 1 = 1) and " +
                    "if (:type > 0, ha.type =:type, 1 = 1) and " +
                    "if (:status > -1, ha.status = :status, 1 = 1) and " +
                    "if (:is_private > -1, ha.is_private = :is_private, 1 = 1) and " +
                    "if (:title <> '',ha.title like CONCAT('%',:title,'%'),1=1) and " +
                    "if (:uid > 0,ha.uid = :uid,1=1) and " +
                    "hac.category_id = :category_id " +
                    "group by ha.id "
    )
    Page<Object[]> searchArticleWithCategoryPaging(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("type") int type,
            @Param("status") int status,
            @Param("is_private") int is_private,
            @Param("title") String title,
            @Param("uid") Integer uid,
            @Param("category_id") int categoryId,
            Pageable pageable
    );

    @Modifying
    int deleteByIdAndUid(Integer id, Integer uid);

    @Query(nativeQuery = true,
            value = "select ha.id,ha.title " +
                    "from hb_article ha " +
                    "where " +
                    "ha.status = 2 and " +
                    "ha.is_private = 0 and " +
                    "if (:uid > 0,ha.uid = :uid,1=1) " +
                    "order by ha.id desc " +
                    "limit 0,:size")
    List<Object[]> findNewestArticle(@Param("uid") Integer uid, @Param("size") Integer size);


    // 查询首页文章列表 使用 Pageable 分页
    // id  uid title introduction category_id   visited_num  comment_num created_at nickname avatar
    @Query(nativeQuery = true,
            value = "select ha.id,ha.uid,ha.title,ha.introduction,ha.category_id,ha.visited_num,ha.comment_num,ha.created_at,hu.nickname,hu.avatar " +
                    "from hb_article ha " +
                    "inner join hb_user hu on hu.id = ha.uid " +
                    "where " +
                    "ha.is_private = 0 and " +
                    "ha.status = 2 and " +
                    "if (:category_id > 0,ha.category_id = :category_id, 1 = 1)",
            countQuery = "select count(ha.id) " +
                    "from hb_article ha " +
                    "inner join hb_user hu on hu.id = ha.uid " +
                    "where " +
                    "ha.is_private = 0 and " +
                    "ha.status = 2 and " +
                    "if (:category_id > 0,ha.category_id = :category_id, 1 = 1)"
    )
    Page<Object[]> findIndexArticle(
            @Param("category_id") int categoryId,
            Pageable pageable
    );

    //获得热门博主
    @Query(
            nativeQuery = true,
            value = "select * from hb_article order by visited_num desc limit 0,?1"
    )
    List<Article> findPopularArticle(Integer size);

    Page<Article> findAllByTitleLikeAndIsPrivateAndStatus(
            @Param("title") String title,
            @Param("is_private") Byte is_private,
            @Param("status") Byte status,
            Pageable pageable
    );


}
