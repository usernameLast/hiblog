package cc.lastone.hiblog.vo;


import cc.lastone.hiblog.domain.Category;
import cc.lastone.hiblog.utils.HtmlRegexpUtil;
import cc.lastone.hiblog.utils.MyDateUtil;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class ArticleSetVo {
    private Integer id;
    private Integer uid;
    @NotEmpty(message = "请填写标题")
    @Length(max = 100, message = "标题最多100字符")
    private String title;
    @NotEmpty(message = "请填写内容")
    private String content;
    private String introduction;
    private String labels;
    @Range(min = 1, message = "请选择博客类型")
    private Byte type;
    @Range(min = 1, message = "请选择博客分类")
    private Integer categoryId;
    private Byte isPrivate;
    private Integer visitedNum;
    private Integer commentNum;
    private String createdAt;
    private Byte status;
    // 分类数组
    private String[] categoryArr;
    // 标签数组
    private String[] labelsArr;

    // 系统分类
    private List<Category> categoryList;

    // 自定义分类
    private List<Category> myCategoryList;

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
        if (content != null && !content.isEmpty()) {
            String temp = HtmlRegexpUtil.filterHtml(content);
            introduction = temp.substring(0, temp.length() > 250 ? 250 : temp.length());
        }
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLabels() {
        // 判断是否有
        if (labelsArr != null && labelsArr.length > 0) {
            labels = "";
            for (String str : labelsArr) {
                labels += str + ",";
            }
            labels = labels.substring(0, labels.length() - 1);
        }
        return labels;
    }

    public void setLabels(String labels) {
        if (labels != null && !labels.isEmpty()) {
            labelsArr = labels.split(",");
        }
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String[] getCategoryArr() {
        return categoryArr;
    }

    public void setCategoryArr(String[] categoryArr) {
        this.categoryArr = categoryArr;
    }

    public String[] getLabelsArr() {
        return labelsArr;
    }

    public void setLabelsArr(String[] labelsArr) {
        this.labelsArr = labelsArr;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Category> getMyCategoryList() {
        return myCategoryList;
    }

    public void setMyCategoryList(List<Category> myCategoryList) {
        this.myCategoryList = myCategoryList;
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

    @Override
    public String toString() {
        return "ArticleSetVo{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", introduction='" + introduction + '\'' +
                ", labels='" + labels + '\'' +
                ", type=" + type +
                ", categoryId=" + categoryId +
                ", isPrivate=" + isPrivate +
                ", visitedNum=" + visitedNum +
                ", commentNum=" + commentNum +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", categoryArr=" + Arrays.toString(categoryArr) +
                ", labelsArr=" + Arrays.toString(labelsArr) +
                ", categoryList=" + categoryList +
                ", myCategoryList=" + myCategoryList +
                '}';
    }
}
