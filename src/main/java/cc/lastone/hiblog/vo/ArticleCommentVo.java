package cc.lastone.hiblog.vo;

import java.util.List;

public class ArticleCommentVo {
    private Integer id;
    private Integer articleId;
    private Integer uid;
    private Integer replyUid;
    private Integer parentId;
    private String content;
    private String createdAt;
    private Byte status;
    private String nickname;
    private String avatar;
    private List<ArticleCommentReplayVo> articleCommentReplayVoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(Integer replyUid) {
        this.replyUid = replyUid;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ArticleCommentReplayVo> getArticleCommentReplayVoList() {
        return articleCommentReplayVoList;
    }

    public void setArticleCommentReplayVoList(List<ArticleCommentReplayVo> articleCommentReplayVoList) {
        this.articleCommentReplayVoList = articleCommentReplayVoList;
    }

    @Override
    public String toString() {
        return "ArticleCommentVo{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", uid=" + uid +
                ", replyUid=" + replyUid +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status=" + status +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", articleCommentReplayVoList=" + articleCommentReplayVoList +
                '}';
    }
}
