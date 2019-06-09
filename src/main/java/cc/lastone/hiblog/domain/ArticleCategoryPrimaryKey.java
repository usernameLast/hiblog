package cc.lastone.hiblog.domain;

import java.io.Serializable;

public class ArticleCategoryPrimaryKey implements Serializable {
    private Integer articleId;
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
}
