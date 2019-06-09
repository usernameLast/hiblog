package cc.lastone.hiblog.vo;


import cc.lastone.hiblog.to.ArticleCategoryTo;
import cc.lastone.hiblog.utils.MyDateUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class ArticleDetailVo {
    private Integer id;
    private Integer uid;
    private String title;
    private String content;
    private Integer visitedNum;
    private Integer commentNum;
    private String createdAt;
    private Byte status;
    private Byte type;
    private Integer categoryId;
    private Byte isPrivate;
    private String labels;
    // 分类数组
    private List<ArticleCategoryTo> articleCategoryList;
    // 标签数组
    private String[] labelsArr;
    // 是否收藏  -1，不需要判断  0未收藏，1已收藏
    private Integer collectType;

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

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        if (labels != null && !labels.isEmpty()) {
            labelsArr = labels.split(",");
        }
        this.labels = labels;
    }

    public List<ArticleCategoryTo> getArticleCategoryList() {
        return articleCategoryList;
    }

    public void setArticleCategoryList(List<ArticleCategoryTo> articleCategoryList) {
        this.articleCategoryList = articleCategoryList;
    }

    public String[] getLabelsArr() {
        return labelsArr;
    }

    public void setLabelsArr(String[] labelsArr) {
        this.labelsArr = labelsArr;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    @Override
    public String toString() {
        return "ArticleDetailVo{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", visitedNum=" + visitedNum +
                ", commentNum=" + commentNum +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", type=" + type +
                ", categoryId=" + categoryId +
                ", isPrivate=" + isPrivate +
                ", labels='" + labels + '\'' +
                ", articleCategoryList=" + articleCategoryList +
                ", labelsArr=" + Arrays.toString(labelsArr) +
                ", collectType=" + collectType +
                '}';
    }
}
