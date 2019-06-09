package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Integer> {

    Page<ArticleComment> findAllByArticleIdAndParentIdOrderByIdDesc(Integer articleId, Integer parentId, Pageable pageable);

    List<ArticleComment> findAllByParentIdOrderByIdDesc(Integer parentId);

    Page<ArticleComment> findAllByArticleUidOrReplyUidOrderByIdDesc(Integer articleUid, Integer replyUid, Pageable pageable);

    Page<ArticleComment> findAllByUidOrderByIdDesc(Integer uid, Pageable pageable);

    @Modifying
    @Query(value = "delete from ArticleComment ac where (ac.id = ?1 or ac.parentId = ?1) and (ac.uid = ?2 or ac.articleUid = ?2)")
    int deleteComment(Integer id, Integer uid);

}
