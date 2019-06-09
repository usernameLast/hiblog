package cc.lastone.hiblog.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "hb_article_comment")
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Integer articleId;
    private Integer articleUid;
    private Integer uid;
    private Integer replyUid;
    private Integer parentId;
    @NotNull
    @Length(max = 500, message = "评论最多500字")
    private String content;
    private String createdAt;
    private Byte status;

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

    public Integer getArticleUid() {
        return articleUid;
    }

    public void setArticleUid(Integer articleUid) {
        this.articleUid = articleUid;
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

    @Override
    public String toString() {
        return "ArticleComment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", articleUid=" + articleUid +
                ", uid=" + uid +
                ", replyUid=" + replyUid +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status=" + status +
                '}';
    }
}
