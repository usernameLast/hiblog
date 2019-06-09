package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.Follow;
import cc.lastone.hiblog.domain.FollowPrimaryKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, FollowPrimaryKey> {

    int countByUidAndFollowId(Integer uid, Integer followId);

    // 查询我的关注列表 使用 Pageable 分页
    @Query(nativeQuery = true,
            value = "select  hf.uid,hu.nickname,hu.avatar " +
                    "from hb_follow hf " +
                    "inner join hb_user hu on hu.id = hf.follow_id " +
                    "where hf.uid = :uid",
            countQuery = "select  count(hf.uid) " +
                    "from hb_follow hf " +
                    "inner join hb_user hu on hu.id = hf.follow_id " +
                    "where hf.uid = :uid"
    )
    Page<Object[]> getFollowUser(
            @Param("uid") Integer uid,
            Pageable pageable
    );

    // 查询我的粉丝列表 使用 Pageable 分页
    @Query(nativeQuery = true,
            value = "select  hf.uid,hu.nickname,hu.avatar " +
                    "from hb_follow hf " +
                    "inner join hb_user hu on hu.id = hf.uid " +
                    "where hf.follow_id = :uid",
            countQuery = "select  count(hf.uid) " +
                    "from hb_follow hf " +
                    "inner join hb_user hu on hu.id = hf.uid " +
                    "where hf.follow_id = :uid"
    )
    Page<Object[]> getFansUser(
            @Param("uid") Integer uid,
            Pageable pageable
    );

    int deleteByUidAndFollowId(Integer uid, Integer followId);


}
