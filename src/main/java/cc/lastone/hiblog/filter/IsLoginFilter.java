package cc.lastone.hiblog.filter;

import cc.lastone.hiblog.cons.UserCons;
import cc.lastone.hiblog.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IsLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        if (path.contains("css") || path.contains("js") || path.contains("/images") || path.contains("/uploadDir")) {
//            System.out.println("没过滤:" + path);
        } else {
//            System.out.println("过滤了:" + path);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(UserCons.LOGIN_SESSION_NAME);
            Boolean IsLogin = true;
            if (user == null) {
                IsLogin = false;
            }
//            System.out.println(IsLogin);
            request.setAttribute("userIsLogin", IsLogin);
            request.setAttribute("loginSessionUserInfo", user);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
