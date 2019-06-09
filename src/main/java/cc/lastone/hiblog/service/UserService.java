package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.to.FollowUserTo;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.vo.BlogUserVo;
import cc.lastone.hiblog.vo.EditUserInfoVo;
import cc.lastone.hiblog.vo.RegisterVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    User check(String username, String password);

    ResultData<User> register(RegisterVo rv);

    User save(User user);

    EditUserInfoVo getUserInfo(Integer id);

    BlogUserVo getBlogUserInfo(Integer id, Integer currentUid);

    ResultData followUser(Integer uid, Integer follow_id);

    ResultData cancelFollowUser(Integer uid, Integer follow_id);

    Map getFollowUser(Integer uid, Pageable pageable);

    Map getFansUser(Integer uid, Pageable pageable);

    void setUserFollowAndFansNum(Integer uid, Integer follow_id, Integer num);

    List<User> getPopularUser(Integer size);

    Map searchUser(String nickname, Pageable pageable);
}
