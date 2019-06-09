package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.Category;
import cc.lastone.hiblog.service.CategoryService;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping({"/myCategory"})
public class MyCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 个人分类管理
     */
    @RequestMapping({""})
    public String index(Model model) {
        ArrayList<Category> categoryList = (ArrayList<Category>) categoryService.getListByUId(UserUtil.getUserId());
        model.addAttribute("articleCategoryList", categoryList);
        model.addAttribute("myArticleRotuer", "myCategory");
        return "myArticle/myCategory";
    }

    /**
     * 添加个人分类
     * @param name
     * @return
     */
    @RequestMapping({"/add"})
    @ResponseBody
    public ResultData add(String name) {
        if (name == null || name.isEmpty()) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        Category category = new Category();
        category.setName(name);
        category.setUid(UserUtil.getUserId());
        return categoryService.add(category);
    }

    /**
     * 编辑个人分类
     * @param category
     * @return
     */
    @RequestMapping({"/edit"})
    @ResponseBody
    public ResultData edit(Category category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        category.setUid(UserUtil.getUserId());
        category = categoryService.edit(category);
        if (category == null) {
            return new ResultData(ResultData.ERROR_SAVE, "修改分类失败");
        }
        return new ResultData("修改分类成功");
    }

    /**
     * 移动个人分类
     * @param id
     * @param type
     * @return
     */
    @RequestMapping({"/move"})
    @ResponseBody
    public ResultData move(Integer id, Integer type) {
        return categoryService.setSort(id, type, UserUtil.getUserId());
    }

    /**
     * 删除个人分类
     * @param id
     * @return
     */
    @RequestMapping({"/delete"})
    @ResponseBody
    public ResultData delete(Integer id) {
        return categoryService.delete(id, UserUtil.getUserId());
    }

}
