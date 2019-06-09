package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.Article;
import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.service.ArticleCommentService;
import cc.lastone.hiblog.service.ArticleService;
import cc.lastone.hiblog.service.CategoryService;
import cc.lastone.hiblog.service.UserService;
import cc.lastone.hiblog.to.ArticleCategoryTo;
import cc.lastone.hiblog.to.NewestArticleTo;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import cc.lastone.hiblog.vo.ArticleDetailVo;
import cc.lastone.hiblog.vo.BlogUserVo;
import cc.lastone.hiblog.vo.MyArticleCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleCommentService articleCommentService;

    /**
     * 用户首页
     *
     * @param id
     * @return
     */
    @RequestMapping({"", "/{id}", "/{id}/category/{category}"})
    public String index(@PathVariable(name = "id", required = false) Integer id,
                        @PathVariable(name = "category", required = false) Integer category,
                        @RequestParam(name = "type", defaultValue = "0") Integer type,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "order", defaultValue = "default") String order,
                        Model model) {
        Integer currentUid = UserUtil.getUserId();
        id = id == null ? currentUid : id;
        if (id == 0) {
            return "error/404";
        }
        // 获得博主信息
        BlogUserVo blogUserVo = userService.getBlogUserInfo(id, currentUid);
        if (blogUserVo == null) {
            return "error/404";
        }
        model.addAttribute("blogUserInfo", blogUserVo);

        // 添加用户浏览次数
        User setUser = new User();
        setUser.setId(id);
        setUser.setVisitedNum(blogUserVo.getVisitedNum() + 1);
        userService.save(setUser);

        // 获得博客列表
        category = category == null ? 0 : category;
        // 1.设置分页
        page = page > 0 ? page - 1 : 0;
        String orderBy = "top_timestamp";
        if (order.equals("updateOrder")) {
            orderBy = "created_at";
        } else if (order.equals("visitedOrder")) {
            orderBy = "visited_num";
        }
        // 添加分页
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, orderBy, "id"));
        // 获得博客数据和分页信息
        model.addAttribute("articlePaging", articleService.searchArticle(null, null, type, 2, 0, "", id, category, pageRequest));
        model.addAttribute("blogOrderBy", order);
        model.addAttribute("blogType", type);

        // 获得最新博客
        List<NewestArticleTo> newestArticleToList = articleService.getNewstArticleList(blogUserVo.getId(), 5);
        // 获得个人分类
        List<ArticleCategoryTo> articleCategoryToList = categoryService.getCagegoryArticleNum(blogUserVo.getId());
        // 获得分类名称
        String blogCategoryName = "";
        if (category > 0) {
            for (ArticleCategoryTo articleCategoryTo : articleCategoryToList) {
                if (articleCategoryTo.getId().equals(category)) {
                    blogCategoryName = articleCategoryTo.getName();
                    break;
                }
            }
        }
        // 获得最新评论
        List<MyArticleCommentVo> articleCommentVoList = (List<MyArticleCommentVo>) articleCommentService.getMyArticleComment(UserUtil.getUserId(), PageRequest.of(0, 5)).get("myArticleCommentVoList");
        model.addAttribute("newestArticleCommentList", articleCommentVoList);

        model.addAttribute("newestArticleList", newestArticleToList);
        model.addAttribute("articleCategoryList", articleCategoryToList);
        model.addAttribute("blogCategoryName", blogCategoryName);
        model.addAttribute("blogCategory", category);
        model.addAttribute("webTitle", "【" + blogUserVo.getNickname() + "的博客】");
        return "user/index";
    }

    /**
     * 用户博客页面
     *
     * @param id
     * @return
     */
    @RequestMapping({"/article/{id}"})
    public String article(@PathVariable(name = "id") Integer id, Model model) {
        // 获得当前用户uid
        Integer currentUid = UserUtil.getUserId();
        // 获得博客内容
        ArticleDetailVo articleDetailVo = articleService.getArticleShowInfo(id, currentUid);
//        System.out.println("------------1------------");
        // 判断是否可以查看，是否是私密博客,博客不能查看
        if (articleDetailVo == null || (articleDetailVo.getIsPrivate() == 1 && !articleDetailVo.getUid().equals(currentUid))) {
            return "error/404";
        }
        model.addAttribute("articleDetailInfo", articleDetailVo);
        // 获得博主信息
        BlogUserVo blogUserVo = userService.getBlogUserInfo(articleDetailVo.getUid(), currentUid);
//        System.out.println("------------2------------");
        if (blogUserVo == null) {
            return "error/404";
        }
        model.addAttribute("blogUserInfo", blogUserVo);
        // 添加博客浏览次数
        Article setArticle = new Article();
        setArticle.setId(articleDetailVo.getId());
        setArticle.setVisitedNum(articleDetailVo.getVisitedNum() + 1);
        articleService.editArticle(setArticle, articleDetailVo.getUid());
//        System.out.println("------------3------------");

        // 获得最新博客
        List<NewestArticleTo> newestArticleToList = articleService.getNewstArticleList(blogUserVo.getId(), 5);
        // 获得个人分类
        List<ArticleCategoryTo> articleCategoryToList = categoryService.getCagegoryArticleNum(blogUserVo.getId());
        model.addAttribute("newestArticleList", newestArticleToList);
        model.addAttribute("articleCategoryList", articleCategoryToList);
        // 获得最新评论
        List<MyArticleCommentVo> articleCommentVoList = (List<MyArticleCommentVo>) articleCommentService.getMyArticleComment(UserUtil.getUserId(), PageRequest.of(0, 5)).get("myArticleCommentVoList");
        model.addAttribute("newestArticleCommentList", articleCommentVoList);
        model.addAttribute("webTitle", articleDetailVo.getTitle() + " - " + blogUserVo.getNickname() + "的博客");
        return "user/article";
    }

    /**
     * 用户评论
     *
     * @param id
     * @return
     */
    @RequestMapping({"/comment/{id}"})
    @ResponseBody
    public ResultData comment(@PathVariable(name = "id") Integer id,
                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        if (id == null) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size);
        return new ResultData(articleCommentService.getArticleComment(id, pageRequest));
    }

}
