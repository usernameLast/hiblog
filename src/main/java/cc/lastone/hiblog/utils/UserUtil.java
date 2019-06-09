package cc.lastone.hiblog.utils;

import cc.lastone.hiblog.cons.UserCons;
import cc.lastone.hiblog.domain.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserUtil {
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获得加密后的密码
     *
     * @param password String
     * @param salt     String
     * @return String
     */
    public static String setPassword(String password, String salt) {
        return MySecurityUtil.md5(MySecurityUtil.md5(password) + salt);
    }

    /**
     * 获得当前登陆用户的uid
     *
     * @return Integer
     */
    public static Integer getUserId() {
        HttpSession session = getSession();
        User user = (User) session.getAttribute(UserCons.LOGIN_SESSION_NAME);
        Integer uid = 0;
        if (user != null) {
            uid = user.getId();
        }
        return uid;
    }

    /**
     * 获得用户默认头像
     */
    public static String getDefaultAvatar() {
        return getRequest().getContextPath() + "/images/defaultAvatar.png";
    }

    /**
     * 保存登陆用户信息
     */
    public static void setSessionUser(User user) {
        setSessionUser(user, null);
    }

    public static void setSessionUser(User user, String remember_me) {
        // 添加到session中
        HttpSession session = getSession();
        session.setAttribute(UserCons.LOGIN_SESSION_NAME, user);
        if (remember_me != null && remember_me.equals("1")) {
            session.setMaxInactiveInterval(864000);
        }
    }

    public static void removeSessionUser() {
        getSession().removeAttribute(UserCons.LOGIN_SESSION_NAME);
    }

    public static User getSessionUser() {
        return (User) getSession().getAttribute(UserCons.LOGIN_SESSION_NAME);
    }

}
