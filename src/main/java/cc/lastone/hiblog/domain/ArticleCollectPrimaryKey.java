package cc.lastone.hiblog.domain;

import java.io.Serializable;

public class ArticleCollectPrimaryKey implements Serializable {
    private Integer uid;
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
        return "ArticleCollectPrimaryKey{" +
                "uid=" + uid +
                ", articleId=" + articleId +
                '}';
    }
}
