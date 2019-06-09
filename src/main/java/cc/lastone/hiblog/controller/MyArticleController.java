package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.Article;
import cc.lastone.hiblog.service.ArticleService;
import cc.lastone.hiblog.service.CategoryService;
import cc.lastone.hiblog.utils.MyDateUtil;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import cc.lastone.hiblog.vo.ArticleSetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping({"/myArticle"})
public class MyArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 博客管理
     */
    @RequestMapping({""})
    public String index(@RequestParam(name = "year", defaultValue = "0") String year,
                        @RequestParam(name = "month", defaultValue = "0") String month,
                        @RequestParam(name = "type", defaultValue = "0") Integer type,
                        @RequestParam(name = "is_private", defaultValue = "-1") Integer is_private,
                        @RequestParam(name = "status", defaultValue = "-1") Integer status,
                        @RequestParam(name = "category_id", defaultValue = "0") Integer category_id,
                        @RequestParam(name = "title", defaultValue = "") String title,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "choose_tab", defaultValue = "myArticleIndex") String choose_tab,
                        Model model) {
        Integer uid = UserUtil.getUserId();
        // 注册年份
        Integer createdYear = Integer.parseInt(MyDateUtil.date("yyyy", MyDateUtil.strtotime(MyDateUtil.DATE_TIME_FORMAT, UserUtil.getSessionUser().getCreatedAt())));
        // 当前年份
        Integer nowYear = Integer.parseInt(MyDateUtil.date("yyyy"));
        nowYear = 2030;
        String[] yearList = new String[nowYear - createdYear + 1];
        for (int i = 0; nowYear >= createdYear + i; i++) {
            yearList[i] = String.valueOf(createdYear + i);
        }

        // 生成日期
        String startDate = null;
        String endDate = null;
        if (!year.equals("0")) {
            if (month.equals("0")) {
                startDate = year;
                endDate = String.valueOf(Integer.parseInt(year) + 1);
            } else {
                startDate = year + "-" + month + "-" + "01";
                endDate = Integer.parseInt(month) == 12 ? Integer.parseInt(year) + 1 + "-01-01" : year + "-" + (Integer.parseInt(month) + 1) + "-01";
            }
        }
        // 1.设置分页
        page = page > 0 ? page - 1 : 0;
        // 添加分页
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "top_timestamp", "id"));
        // 获得博客数据和分页信息
        model.addAttribute("articlePaging", articleService.searchArticle(startDate, endDate, type, status, is_private, title, uid, category_id, pageRequest));
        //获得个人分类
        model.addAttribute("myCategoryList", categoryService.getListByUId(uid));
        model.addAttribute("yearList", yearList);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("type", type);
        model.addAttribute("is_private", is_private);
        model.addAttribute("status", status);
        model.addAttribute("category_id", category_id);
        model.addAttribute("title", title);
        model.addAttribute("myArticleRotuer", "index");
        model.addAttribute("choose_tab", choose_tab);
        return "myArticle/index";
    }

    /**
     * 写博客
     */
    @RequestMapping({"/write"})
    public String write(@RequestParam(name = "id", defaultValue = "0") String id, Model model) {
        ArticleSetVo articleSetVo = articleService.getArticleSetInfo(UserUtil.getUserId(), Integer.parseInt(id));
        if (articleSetVo == null) {
            return "error/404";
        }
        model.addAttribute("myArticleRotuer", "write");
        model.addAttribute("articleSetVo", articleSetVo);
        return "myArticle/write";
    }

    /**
     * 保存博客
     *
     * @param articleSetVo
     * @param bindingResult
     * @return
     */
    @RequestMapping({"/setArticleInfo"})
    @ResponseBody
    public ResultData setArticleInfo(@Valid ArticleSetVo articleSetVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultData(ResultData.ERROR_PARAM, bindingResult.getFieldError().getDefaultMessage());
        }
        return articleService.setArticleInfo(articleSetVo, UserUtil.getUserId());
    }

    /**
     * 删除博客
     *
     * @param id
     * @return
     */
    @RequestMapping({"/delete"})
    @ResponseBody
    public ResultData delete(@RequestParam(name = "id", defaultValue = "0") Integer id) {
        if (id == 0) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        return articleService.deleteMyArtilce(id, UserUtil.getUserId());
    }

    /**
     * 博客置顶
     *
     * @param id
     * @return
     */
    @RequestMapping({"/toTop"})
    @ResponseBody
    public ResultData toTop(@RequestParam(name = "id", defaultValue = "0") Integer id) {
        if (id == 0) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        Article article = new Article();
        article.setId(id);
        article.setTopTimestamp((int) (new Date().getTime() / 1000));
        return articleService.editArticle(article, UserUtil.getUserId());
    }


}
