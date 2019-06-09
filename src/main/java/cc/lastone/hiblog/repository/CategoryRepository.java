package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    int countByUidAndName(Integer uid, String name);

    List<Category> findAllByUidOrderBySnAsc(Integer uid);

    Category findByIdAndUid(Integer id, Integer uid);

    Category findByNameAndUid(String name, Integer uid);

    @Modifying
    int deleteByIdAndUid(Integer id, Integer uid);


    /**
     * 下移一位   需要查询出   sn  大一位的
     */
    List<Category> findByUidAndSnGreaterThanOrderBySnAsc(Integer uid, Integer sn);

    /**
     * 查询出第一位的排序
     */
    List<Category> findByUidOrderBySnAsc(Integer uid);

    /**
     * 上移一位  需要查询出  sn 小一个的
     */
    List<Category> findByUidAndSnLessThanOrderBySnDesc(Integer uid, Integer sn);


    @Modifying
    @Query("update Category c set c.sn = c.sn + 1 where c.uid = :uid and c.sn < :sn")
    int topSn(@Param(value = "uid") Integer uid, @Param(value = "sn") Integer sn);

    @Query(
            nativeQuery = true,
            value = "select hc.id,hc.name " +
                    "from hb_category hc " +
                    "inner join hb_article_category hac on hc.id = hac.category_id " +
                    "where hac.article_id = ?1 " +
                    "order by sn asc"
    )
    List<Object[]> findArticleCategory(Integer articleId);

    @Query(
            nativeQuery = true,
            value = "SELECT hac.category_id,hc.name,count(hac.article_id) as num " +
                    "from hb_article_category hac " +
                    "inner join hb_category hc on hac.category_id = hc.id  " +
                    "where hc.uid = ?1 " +
                    "GROUP BY hac.category_id"
    )
    List<Object[]> findArticleNumByUid(Integer uid);

    List<Category> findAllByUidAndIsShowOrderBySnAsc(Integer uid, Byte isSHow);


}
