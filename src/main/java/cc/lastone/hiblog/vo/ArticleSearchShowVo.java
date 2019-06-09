package cc.lastone.hiblog.vo;


import cc.lastone.hiblog.utils.MyDateUtil;

import java.util.Date;

public class ArticleSearchShowVo {

    // id  uid title introduction   type  is_private  visited_num  comment_num top_timestamp created_at  status

    private Integer id;
    private Integer uid;
    private String title;
    private String introduction;
    private Byte type;
    private Integer categoryId;
    private Byte isPrivate;
    private Integer visitedNum;
    private Integer commentNum;
    private Integer topTimestamp;
    private Date createdAt;
    private Byte status;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Byte isPrivate) {
        this.isPrivate = isPrivate;
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

    public Integer getTopTimestamp() {
        return topTimestamp;
    }

    public void setTopTimestamp(Integer topTimestamp) {
        this.topTimestamp = topTimestamp;
    }

    public String getCreatedAt() {
        return MyDateUtil.date(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArticleSearchShowVo{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", type=" + type +
                ", categoryId=" + categoryId +
                ", isPrivate=" + isPrivate +
                ", visitedNum=" + visitedNum +
                ", commentNum=" + commentNum +
                ", topTimestamp=" + topTimestamp +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
