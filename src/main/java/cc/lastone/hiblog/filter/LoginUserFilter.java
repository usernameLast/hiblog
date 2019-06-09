package cc.lastone.hiblog.filter;

import cc.lastone.hiblog.cons.UserCons;
import cc.lastone.hiblog.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserCons.LOGIN_SESSION_NAME);
        if (user == null) {
            // 保存从哪来的页面
            String url = request.getRequestURL().toString();
            if (!url.isEmpty() && !url.contains("login")) {
                session.setAttribute(UserCons.FROM_URL, url);
            }
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(request.getContextPath() + "/login");
            //servletRequest.getRequestDispatcher("/login").forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
