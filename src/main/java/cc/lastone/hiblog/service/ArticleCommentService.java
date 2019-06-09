package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.ArticleComment;
import cc.lastone.hiblog.utils.ResultData;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ArticleCommentService {
    ResultData addComment(ArticleComment articleComment,Integer checkUser);

    Map getArticleComment(Integer articleId, Pageable pageable);

    Map getMyArticleComment(Integer articleUid, Pageable pageable);

    Map getMyPublishComment(Integer uid, Pageable pageable);

    ResultData deleteComment(Integer id, Integer uid);
}
