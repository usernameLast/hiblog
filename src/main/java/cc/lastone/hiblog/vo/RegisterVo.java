package cc.lastone.hiblog.vo;


import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;


@Component
public class RegisterVo {

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 2, max = 10, message = "用户名为2-10字符")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不少于6位字符")
    private String password;
    @NotEmpty(message = "确认密码不能为空")
    private String comfirmPassword;
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComfirmPassword() {
        return comfirmPassword;
    }

    public void setComfirmPassword(String comfirmPassword) {
        this.comfirmPassword = comfirmPassword;
    }

    @Override
    public String toString() {
        return "RegisterVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", comfirmPassword='" + comfirmPassword + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
