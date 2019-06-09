package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Modifying
    @Query("update User u set u.articleNum = u.articleNum + ?2 where u.id = ?1")
    int updateUserArticleNum(Integer id, Integer num);

    @Override
    Optional<User> findById(Integer integer);

    User findByIdAndNickname(Integer id, String nickname);

    //获得热门博主
    @Query(
            nativeQuery = true,
            value = "select * from hb_user order by visited_num desc limit 0,?1 "
    )
    List<User> findPopularUser(Integer size);

    Page<User> findAllByNicknameLikeAndStatus(
            @Param("nickname") String nickname,
            @Param("status") Byte status,
            Pageable pageable
    );
}
