package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.cons.UserCons;
import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.service.UserService;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * 登陆首页
     *
     * @return
     */
    @RequestMapping("/login")
    public String index() {
        // 判断是否登录
        User user = UserUtil.getSessionUser();
        if (user != null) {
            return "redirect:/";
        }
        return "login/index";
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        // 判断是否登录
        UserUtil.removeSessionUser();
        return "redirect:/";
    }

    /**
     * 验证登录信息
     *
     * @param username
     * @param password
     * @param remember_me
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/login/check")
    public String check(String username, String password, String remember_me, Model model, HttpServletRequest request) {
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("message", "参数错误");
        } else {
            User user = userService.check(username, password);
            if (user == null) {
                model.addAttribute("message", "账号或密码错误");
            } else {
                // 添加到session中
                HttpSession session = request.getSession();
                UserUtil.setSessionUser(user, remember_me);
                // 判断是否有重定向页面
                String url = (String) session.getAttribute(UserCons.FROM_URL);
                if (url != null && !url.isEmpty() && url.contains("login")) {
                    return "redirect:" + url;
                }
                return "redirect:/";
            }
        }
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("remember_me", remember_me);
        return "login/index";
    }

    /**
     * 验证登录信息
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login/ajaxLogin")
    @ResponseBody
    public ResultData ajaxLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        User user = userService.check(username, password);
        if (user == null) {
            return new ResultData(ResultData.ERROR_PARAM, "账号或密码错误");
        }
        // 添加到session中
        UserUtil.setSessionUser(user);
        return new ResultData("登陆成功");
    }

}
