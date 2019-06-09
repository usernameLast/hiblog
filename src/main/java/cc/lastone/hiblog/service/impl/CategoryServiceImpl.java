package cc.lastone.hiblog.service.impl;


import cc.lastone.hiblog.domain.Category;
import cc.lastone.hiblog.repository.ArticleCategoryRepository;
import cc.lastone.hiblog.repository.CategoryRepository;
import cc.lastone.hiblog.service.CategoryService;
import cc.lastone.hiblog.to.ArticleCategoryTo;
import cc.lastone.hiblog.utils.ConvertDataToVoUtil;
import cc.lastone.hiblog.utils.MyBeanUtils;
import cc.lastone.hiblog.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Override
    public List<Category> getListByUId(Integer uid) {
        return categoryRepository.findAllByUidOrderBySnAsc(uid);
    }

    @Override
    public ResultData add(Category category) {
        // 判断名称是否重复
        int total = categoryRepository.countByUidAndName(category.getUid(), category.getName());
        if (total > 0) {
            return new ResultData("分类已存在");
        }
        category = categoryRepository.save(category);
        if (category == null) {
            return new ResultData("添加分类失败");
        }
        category.setSn(category.getId());
        category = categoryRepository.save(category);
        if (category == null) {
            return new ResultData("添加分类失败");
        }
        return new ResultData(category, "添加分类成功");
    }

    @Override
    public Category edit(Category category) {
        Category oldCategory = categoryRepository.findByIdAndUid(category.getId(), category.getUid());
        if (oldCategory == null) {
            return null;
        }
        MyBeanUtils.copyProperties(category, oldCategory);
        categoryRepository.save(oldCategory);
        return oldCategory;
    }

    @Transactional
    @Override
    public ResultData setSort(Integer id, Integer type, Integer uid) {
        // 验证是否可以修改
        Category category = categoryRepository.findByIdAndUid(id, uid);
        if (category == null) {
            return new ResultData(ResultData.ERROR_NODATA, "分类不存在");
        }
        ArrayList<Category> nextCategotyList = null;
        // 置顶
        if (type == 0) {
            nextCategotyList = (ArrayList<Category>) categoryRepository.findByUidOrderBySnAsc(uid);
            if (nextCategotyList == null || nextCategotyList.size() == 0) {
                return new ResultData(ResultData.ERROR_PARAM, "参数错误，无法移动");
            }
            //  < 当前的全部加一
            int i = categoryRepository.topSn(uid, category.getSn());
            if (i == 0) {
                return new ResultData(ResultData.ERROR_SAVE, "排序失败");
            }
            // 更新 当前的排序
            category.setSn(nextCategotyList.get(0).getSn());
        } else {

            switch (type) {
                case -1: // 下移一位
                    nextCategotyList = (ArrayList<Category>) categoryRepository.findByUidAndSnGreaterThanOrderBySnAsc(uid, category.getSn());
                    break;
//                case 0: // 置顶
//                    nextCategotyList = (ArrayList<Category>) categoryRepository.findByUidOrderBySnAsc(uid);
//                    break;
                case 1: // 上移一位
                    nextCategotyList = (ArrayList<Category>) categoryRepository.findByUidAndSnLessThanOrderBySnDesc(uid, category.getSn());
                    break;
            }
            if (nextCategotyList == null || nextCategotyList.size() == 0) {
                return new ResultData(ResultData.ERROR_PARAM, "参数错误，无法移动");
            }
            Category nextCategoty = nextCategotyList.get(0);
            Integer categorySn = nextCategoty.getSn();
            Integer nextCategorySn = category.getSn();
            category.setSn(categorySn);
            nextCategoty.setSn(nextCategorySn);
            nextCategoty = categoryRepository.save(nextCategoty);
            if (nextCategoty == null) {
                return new ResultData(ResultData.ERROR_SAVE, "排序失败");
            }
        }

        category = categoryRepository.save(category);
        if (category == null) {
            return new ResultData(ResultData.ERROR_SAVE, "排序失败");
        }
        return new ResultData("排序成功");
    }

    @Transactional
    @Override
    public ResultData delete(Integer id, Integer uid) {
        // 获得当前分类是否有博客
        if (articleCategoryRepository.countByCategoryId(id) > 0) {
            return new ResultData(ResultData.ERROR_ONUSE, "当前分类已使用，不能删除");
        }
        // 删除
        int result = categoryRepository.deleteByIdAndUid(id, uid);
        if (result > 0) {
            // 删除成功
            return new ResultData("删除成功");
        } else {
            //删除失败
            return new ResultData(ResultData.ERROR_DELETE, "删除失败");
        }
    }

    @Override
    public List<ArticleCategoryTo> getCagegoryArticleNum(Integer uid) {
        // 查询出分类，然后查询出数量
        List<Category> categoryList = categoryRepository.findAllByUidAndIsShowOrderBySnAsc(uid, (byte) 1);
        // 查询出每个分类的数量
        List<ArticleCategoryTo> articleCategoryToList = new ArrayList<ArticleCategoryTo>();
        if (categoryList != null && categoryList.size() > 0) {
            for (Category category : categoryList) {
                ArticleCategoryTo articleCategoryTo = new ArticleCategoryTo();
                articleCategoryTo.setId(category.getId());
                articleCategoryTo.setName(category.getName());
                // 查询出数量
                articleCategoryTo.setNum(articleCategoryRepository.countCategoryId(category.getId()));
                articleCategoryToList.add(articleCategoryTo);
            }
        }
        // return ConvertDataToVoUtil.convert(categoryRepository.findArticleNumByUid(uid), ArticleCategoryTo.class);
        return articleCategoryToList;
    }

    @Override
    public List<Category> getShowCategory(Integer uid) {
        return categoryRepository.findAllByUidAndIsShowOrderBySnAsc(uid, (byte) 1);
    }
}
