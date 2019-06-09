package cc.lastone.hiblog.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "hb_article_collect")
@IdClass(ArticleCollectPrimaryKey.class)
public class ArticleCollect implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "uid")
    private Integer uid;

    @Id
    @Column(name = "article_id")
    private Integer articleId;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "ArticleCollect{" +
                "uid=" + uid +
                ", articleId=" + articleId +
                '}';
    }
}