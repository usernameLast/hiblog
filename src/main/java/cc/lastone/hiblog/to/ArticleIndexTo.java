package cc.lastone.hiblog.to;


import cc.lastone.hiblog.utils.MyDateUtil;

import java.util.Date;

public class ArticleIndexTo {

    // id  uid title introduction category_id   visited_num  comment_num created_at nickname avatar

    private Integer id;
    private Integer uid;
    private String title;
    private String introduction;
    private Integer categoryId;
    private Integer visitedNum;
    private Integer commentNum;
    private Date createdAt;
    private String nickname;
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getVisitedNum() {
        return visitedNum;
    }

    public void setVisitedNum(Integer visitedNum) {
        this.visitedNum = visitedNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getCreatedAt() {
        return MyDateUtil.date(MyDateUtil.DATE_FORMAT, createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return "ArticleIndexTo{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", categoryId=" + categoryId +
                ", visitedNum=" + visitedNum +
                ", commentNum=" + commentNum +
                ", createdAt='" + createdAt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
