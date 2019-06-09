package cc.lastone.hiblog.service.impl;

import cc.lastone.hiblog.domain.Article;
import cc.lastone.hiblog.domain.ArticleComment;
import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.repository.ArticleCommentRepository;
import cc.lastone.hiblog.repository.ArticleRepository;
import cc.lastone.hiblog.repository.UserRepository;
import cc.lastone.hiblog.service.ArticleCommentService;
import cc.lastone.hiblog.utils.MyDateUtil;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.vo.ArticleCommentReplayVo;
import cc.lastone.hiblog.vo.ArticleCommentVo;
import cc.lastone.hiblog.vo.MyArticleCommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultData addComment(ArticleComment articleComment, Integer checkUser) {
        // 查询文章是否存在
        Article article = articleRepository.findById(articleComment.getArticleId()).orElse(null);
        if (article == null) {
            return new ResultData(ResultData.ERROR_NODATA, "博客不存在");
        }
        articleComment.setArticleUid(article.getUid());

        // 设置时间
        articleComment.setCreatedAt(MyDateUtil.date());
        String regex = "\\[replay\\](.*?)\\[/replay\\][\\s]";
        Matcher m = Pattern.compile(regex).matcher(articleComment.getContent());
        if (m.find()) {
            // 回复评论人
            articleComment.setContent(articleComment.getContent().replaceAll(regex, ""));
        } else {
            if (checkUser > 0) {
                // 直接回复博客
                articleComment.setReplyUid(article.getUid());
                articleComment.setParentId(0);
            }
        }
        articleComment = articleCommentRepository.save(articleComment);
        if (articleComment == null) {
            return new ResultData(ResultData.ERROR_SAVE, "评论失败");
        }
        // 修改评论数加一
        User user = userRepository.findById(articleComment.getUid()).orElse(null);
        if (user != null) {
            user.setCommentNum(user.getCommentNum() + 1);
            userRepository.save(user);
        }
        article.setCommentNum(article.getCommentNum() + 1);
        articleRepository.save(article);
        // 修改博客评论加一
        return new ResultData("回复成功");
    }

    @Override
    public Map getArticleComment(Integer articleId, Pageable pageable) {
        Page<ArticleComment> articleCommentPage = articleCommentRepository.findAllByArticleIdAndParentIdOrderByIdDesc(articleId, 0, pageable);
        List<ArticleCommentVo> articleCommentVoList = new ArrayList<>();
        HashMap hm = new HashMap();
        if (articleCommentPage != null) {
            hm.put("nowPage", articleCommentPage.getPageable().getPageNumber() + 1);
            hm.put("totalPage", articleCommentPage.getTotalPages());
            hm.put("totalNum", articleCommentPage.getTotalElements());
            List<ArticleComment> articleCommentList = articleCommentPage.getContent();
            HashMap<Integer, User> userHashMap = new HashMap<>();
            User user = null;
            for (ArticleComment articleComment : articleCommentList) {
                ArticleCommentVo articleCommentVo = new ArticleCommentVo();
                BeanUtils.copyProperties(articleComment, articleCommentVo);
                // 判断是有有用户信息
                user = getCacheUserInfo(articleCommentVo.getUid(), userHashMap);
                articleCommentVo.setNickname(user.getNickname());
                articleCommentVo.setAvatar(user.getAvatar());
                // 查询出回复内容
                List<ArticleComment> articleCommentReplayList = articleCommentRepository.findAllByParentIdOrderByIdDesc(articleCommentVo.getId());
                if (articleCommentReplayList != null) {
                    List<ArticleCommentReplayVo> articleCommentReplayVoList = new ArrayList<>();
                    // 查询出
                    for (ArticleComment articleCommentReplay : articleCommentReplayList) {
                        ArticleCommentReplayVo articleCommentReplayVo = new ArticleCommentReplayVo();
                        BeanUtils.copyProperties(articleCommentReplay, articleCommentReplayVo);
                        // 查询出用户头像和回复用户昵称
                        user = getCacheUserInfo(articleCommentReplayVo.getUid(), userHashMap);
                        articleCommentReplayVo.setNickname(user.getNickname());
                        articleCommentReplayVo.setAvatar(user.getAvatar());

                        articleCommentReplayVo.setReplyNickname((getCacheUserInfo(articleCommentReplayVo.getReplyUid(), userHashMap)).getNickname());

                        articleCommentReplayVoList.add(articleCommentReplayVo);
                    }
                    articleCommentVo.setArticleCommentReplayVoList(articleCommentReplayVoList);
                }
                articleCommentVoList.add(articleCommentVo);
            }
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
        }
        hm.put("articleCommentVoList", articleCommentVoList);
        return hm;
    }

    @Override
    public Map getMyArticleComment(Integer articleUid, Pageable pageable) {
        Page<ArticleComment> articleCommentPage = articleCommentRepository.findAllByArticleUidOrReplyUidOrderByIdDesc(articleUid, articleUid, pageable);
        List<MyArticleCommentVo> myArticleCommentVoList = new ArrayList<>();
        HashMap hm = new HashMap();
        if (articleCommentPage != null) {
            hm.put("nowPage", articleCommentPage.getPageable().getPageNumber() + 1);
            hm.put("totalPage", articleCommentPage.getTotalPages());
            hm.put("totalNum", articleCommentPage.getTotalElements());
            List<ArticleComment> articleCommentList = articleCommentPage.getContent();
            HashMap<Integer, User> userHashMap = new HashMap<>();
            User user = null;
            for (ArticleComment articleComment : articleCommentList) {
                MyArticleCommentVo myArticleCommentVo = new MyArticleCommentVo();
                BeanUtils.copyProperties(articleComment, myArticleCommentVo);
                // 查询出用户信息
                user = getCacheUserInfo(articleComment.getUid(), userHashMap);
                myArticleCommentVo.setNickname(user.getNickname());
                myArticleCommentVo.setAvatar(user.getAvatar());
                // 查询出文章标题
                myArticleCommentVo.setArticleTitle((articleRepository.findById(articleComment.getArticleId()).orElse(new Article())).getTitle());
                myArticleCommentVoList.add(myArticleCommentVo);
            }
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
        }
        hm.put("myArticleCommentVoList", myArticleCommentVoList);
        return hm;
    }

    @Override
    public Map getMyPublishComment(Integer uid, Pageable pageable) {
        Page<ArticleComment> articleCommentPage = articleCommentRepository.findAllByUidOrderByIdDesc(uid, pageable);
        List<MyArticleCommentVo> myArticleCommentVoList = new ArrayList<>();
        HashMap hm = new HashMap();
        if (articleCommentPage != null) {
            hm.put("nowPage", articleCommentPage.getPageable().getPageNumber() + 1);
            hm.put("totalPage", articleCommentPage.getTotalPages());
            hm.put("totalNum", articleCommentPage.getTotalElements());
            List<ArticleComment> articleCommentList = articleCommentPage.getContent();
            HashMap<Integer, User> userHashMap = new HashMap<>();
            User user = null;
            for (ArticleComment articleComment : articleCommentList) {
                MyArticleCommentVo myArticleCommentVo = new MyArticleCommentVo();
                BeanUtils.copyProperties(articleComment, myArticleCommentVo);
                // 查询出用户信息
                user = getCacheUserInfo(articleComment.getArticleUid(), userHashMap);
                myArticleCommentVo.setNickname(user.getNickname());
                // 查询出文章标题
                myArticleCommentVo.setArticleTitle((articleRepository.findById(articleComment.getArticleId()).orElse(new Article())).getTitle());
                myArticleCommentVoList.add(myArticleCommentVo);
            }
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
        }
        hm.put("myArticleCommentVoList", myArticleCommentVoList);
        return hm;
    }

    @Override
    @Transactional
    public ResultData deleteComment(Integer id, Integer uid) {
        // 查询文章
        ArticleComment articleComment = articleCommentRepository.findById(id).orElse(null);
        if (articleComment == null) {
            return new ResultData(ResultData.ERROR_PARAM, "评论不存在");
        }
        int i = articleCommentRepository.deleteComment(id, uid);
        if (i == 0) {
            return new ResultData(ResultData.ERROR_PARAM, "删除失败");
        }
        // 修改文章评论数量
        Article article = articleRepository.findById(articleComment.getArticleId()).orElse(null);
        if (article == null) {
            return new ResultData(ResultData.ERROR_PARAM, "文章不存在");
        }
        article.setCommentNum(article.getCommentNum() - i);
        articleRepository.save(article);
        return new ResultData("删除成功");
    }

    private User getCacheUserInfo(Integer uid, Map<Integer, User> userMap) {
        User user = userMap.get(uid);
        if (user == null) {
            user = userRepository.findById(uid).orElse(new User());
            userMap.put(uid, user);
        }
        return user;
    }
}
