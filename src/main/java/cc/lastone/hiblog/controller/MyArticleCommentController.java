package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.ArticleComment;
import cc.lastone.hiblog.service.ArticleCommentService;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping({"/myArticleComment"})
public class MyArticleCommentController {

    @Autowired
    private ArticleCommentService articleCommentService;

    /**
     * 博客评论管理
     */
    @RequestMapping({""})
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "choose_tab", defaultValue = "myArticleComment") String choose_tab,
                        Model model) {
        // 添加分页
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size);
        // 查询我的文章评论
        if (choose_tab.equals("myArticleComment")) {
            model.addAttribute("articleCommentPaging", articleCommentService.getMyArticleComment(UserUtil.getUserId(), pageRequest));
        } else {
            // 查询我发布的评论
            model.addAttribute("articleCommentPaging", articleCommentService.getMyPublishComment(UserUtil.getUserId(), pageRequest));
        }
        model.addAttribute("choose_tab", choose_tab);
        model.addAttribute("myArticleRotuer", "myArticleComment");
        return "myArticle/myArticleComment";
    }

    @RequestMapping({"/add"})
    @ResponseBody
    public ResultData add(@RequestParam(name = "checkUser", defaultValue = "1") Integer checkUser,
                          @Valid ArticleComment articleComment,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultData(ResultData.ERROR_PARAM, bindingResult.getFieldError().getDefaultMessage());
        }
        System.out.println(checkUser);
        System.out.println(articleComment);
        articleComment.setUid(UserUtil.getUserId());
        return articleCommentService.addComment(articleComment, checkUser);
    }

    @RequestMapping({"/delete"})
    @ResponseBody
    public ResultData delete(Integer id) {
        if (id == null || id < 1) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        return articleCommentService.deleteComment(id, UserUtil.getUserId());
    }
}
