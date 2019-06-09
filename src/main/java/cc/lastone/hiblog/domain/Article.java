package cc.lastone.hiblog.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "hb_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer uid;

    @NotEmpty(message = "请填写标题")
    @Length(max = 100, message = "标题最多100字符")
    private String title;
    @NotEmpty(message = "请填写内容")
    private String content;
    private String introduction;
    private String labels;
    private Byte type;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "is_private")
    private Byte isPrivate;
    @Column(name = "visited_num")
    private Integer visitedNum;
    @Column(name = "comment_num")
    private Integer commentNum;
    @Column(name = "top_timestamp")
    private Integer topTimestamp;
    @Column(name = "created_at")
    private String createdAt;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", introduction='" + introduction + '\'' +
                ", labels='" + labels + '\'' +
                ", type=" + type +
                ", categoryId=" + categoryId +
                ", isPrivate=" + isPrivate +
                ", visitedNum=" + visitedNum +
                ", commentNum=" + commentNum +
                ", topTimestamp=" + topTimestamp +
                ", createdAt='" + createdAt + '\'' +
                ", status=" + status +
                '}';
    }
}
