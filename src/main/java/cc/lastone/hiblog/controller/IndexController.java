package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.service.ArticleService;
import cc.lastone.hiblog.service.CategoryService;
import cc.lastone.hiblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping({"/", "/index"})
    public String index(@RequestParam(name = "category_id", defaultValue = "0") Integer category_id,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        Model model) {
        // 查询出分类
        model.addAttribute("categoryList", categoryService.getShowCategory(0));
        model.addAttribute("category_id", category_id);
        // 查询出文章
        model.addAttribute("articlePaging", articleService.getIndexArticle(category_id, PageRequest.of(page > 0 ? page - 1 : 0, size, new Sort(Sort.Direction.DESC, "visited_num"))));
        // 查询出热门博主
        model.addAttribute("popularUserList", userService.getPopularUser(5));
        // 查询出热门博客
        model.addAttribute("popularArticleList", articleService.getPopularArticle(5));
        return "index/index";
    }

    /**
     * 搜索
     *
     * @return
     */
    @RequestMapping({"/search"})
    public String search(@RequestParam(name = "searchType", defaultValue = "0") Integer searchType,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                         Model model) {
        if (keyword != null && !keyword.equals("")) {
            // 查询出内容
            // 查询博客
            if (searchType == 0) {
                // 查询出文章
                model.addAttribute("searchPaging", articleService.searchArticle("%" + keyword + "%", PageRequest.of(page > 0 ? page - 1 : 0, size, new Sort(Sort.Direction.DESC, "visitedNum"))));
            } else {
                // 查询 用户
                model.addAttribute("searchPaging", userService.searchUser("%" + keyword + "%", PageRequest.of(page > 0 ? page - 1 : 0, size, new Sort(Sort.Direction.DESC, "visitedNum"))));
            }
            model.addAttribute("hasSearch", true);
        } else {
            model.addAttribute("hasSearch", false);
        }
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("searchType", searchType);
        return "index/search";
    }

}
