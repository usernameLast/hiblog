package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.Category;
import cc.lastone.hiblog.to.ArticleCategoryTo;
import cc.lastone.hiblog.utils.ResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getListByUId(Integer uid);

    ResultData add(Category category);

    Category edit(Category category);

    ResultData setSort(Integer id, Integer type, Integer uid);

    ResultData delete(Integer id, Integer uid);

    List<ArticleCategoryTo> getCagegoryArticleNum(Integer uid);

    List<Category> getShowCategory(Integer uid);

}
