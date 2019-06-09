package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.service.UserService;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import cc.lastone.hiblog.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 注册首页
     *
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public String index(HttpServletRequest request) {
        // 判断是否登录
        User user = UserUtil.getSessionUser();
        if (user != null) {
            return "redirect:/";
        }
        return "register/index";
    }

    /**
     * 注册
     *
     * @param rv
     * @param bindingResult
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/register/add")
    public String add(@Valid RegisterVo rv, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getFieldError().getDefaultMessage());
        } else {
            // 验证确认密码
            if (rv.getComfirmPassword().equals(rv.getPassword())) {
                // 添加用户
                ResultData rd = userService.register(rv);
                if (rd.getState() == 0) {
                    UserUtil.setSessionUser((User) rd.getData(), "1");
                    return "redirect:/";
                }
                model.addAttribute("message", rd.getMsg());
            } else {
                model.addAttribute("message", "密码和确认密码不一致");
            }
        }
        model.addAttribute("rv", rv);
        return "/register/index";
    }

    /**
     * 注册
     *
     * @param rv
     * @param bindingResult
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/register/ajaxRegister")
    @ResponseBody
    public ResultData ajaxRegister(@Valid RegisterVo rv, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        // 验证确认密码
        if (!rv.getComfirmPassword().equals(rv.getPassword())) {
            return new ResultData(ResultData.ERROR_PARAM, "密码和确认密码不一致");
        }
        ResultData rd = userService.register(rv);
        if (rd.getState() != 0) {
            return rd;
        }
        UserUtil.setSessionUser((User) rd.getData());
        return new ResultData("注册成功");
    }

}
