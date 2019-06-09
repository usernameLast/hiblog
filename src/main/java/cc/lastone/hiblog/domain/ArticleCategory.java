package cc.lastone.hiblog.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "hb_article_category")
@IdClass(ArticleCategoryPrimaryKey.class)
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "article_id")
    private Integer articleId;

    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "articleId=" + articleId +
                ", categoryId=" + categoryId +
                '}';
    }
}

