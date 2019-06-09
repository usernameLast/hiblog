package cc.lastone.hiblog.service.impl;

import cc.lastone.hiblog.domain.Area;
import cc.lastone.hiblog.domain.Follow;
import cc.lastone.hiblog.domain.Industry;
import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.repository.AreaRepository;
import cc.lastone.hiblog.repository.FollowRepository;
import cc.lastone.hiblog.repository.IndustaryRepository;
import cc.lastone.hiblog.repository.UserRepository;
import cc.lastone.hiblog.service.UserService;
import cc.lastone.hiblog.to.FollowUserTo;
import cc.lastone.hiblog.utils.*;
import cc.lastone.hiblog.vo.BlogUserVo;
import cc.lastone.hiblog.vo.EditUserInfoVo;
import cc.lastone.hiblog.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private IndustaryRepository industaryRepository;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public User check(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        password = UserUtil.setPassword(password, user.getSalt());
//        System.out.println(password);
//        System.out.println(myInfo.getPassword());
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public ResultData<User> register(RegisterVo rv) {
        // 判断用户是否存在
        User u = userRepository.findByUsername(rv.getUsername());
        if (u != null) {
            return new ResultData<User>(ResultData.ERROR_EXIST, "账号已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(rv, user);
        user.setSalt(MyRandomUtil.randomStr(6));
        user.setPassword(UserUtil.setPassword(user.getPassword(), user.getSalt()));
        user.setCreatedAt(MyDateUtil.date());
        // 添加默认头像
        user.setAvatar(UserUtil.getDefaultAvatar());
        userRepository.save(user);
        return new ResultData<User>(user);
    }

    @Override
    public User save(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        // 数据不存在
        if (!optionalUser.isPresent()) {
            return null;
        }
        User oldUser = optionalUser.get();
        MyBeanUtils.copyProperties(user, oldUser);
        userRepository.save(oldUser);
        return oldUser;
    }

    @Override
    public EditUserInfoVo getUserInfo(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        // 数据不存在
        if (!optionalUser.isPresent()) {
            return null;
        }
        User user = optionalUser.get();
        EditUserInfoVo editUserInfoVo = new EditUserInfoVo();
        BeanUtils.copyProperties(user, editUserInfoVo);
        // 获得地址
        String address = "";
        Area area = new Area();
        address += editUserInfoVo.getProvinceId() > 0 ?
                areaRepository.findById(editUserInfoVo.getProvinceId()).orElse(area).getName() :
                "";
        address += editUserInfoVo.getCityId() > 0 ?
                areaRepository.findById(editUserInfoVo.getCityId()).orElse(area).getName() :
                "";
        address += editUserInfoVo.getDistrictId() > 0 ?
                areaRepository.findById(editUserInfoVo.getDistrictId()).orElse(area).getName() :
                "";
        editUserInfoVo.setAddress(address);
        // 获得行业
        editUserInfoVo.setIndustryName(
                editUserInfoVo.getIndustryId() > 0 ?
                        industaryRepository.findById(editUserInfoVo.getIndustryId()).orElse(new Industry()).getName() :
                        ""
        );
        return editUserInfoVo;
    }

    @Override
    public BlogUserVo getBlogUserInfo(Integer id, Integer currentUid) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        BlogUserVo blogUserVo = new BlogUserVo();
        BeanUtils.copyProperties(user, blogUserVo);
        if (currentUid == 0 || currentUid.equals(blogUserVo.getId())) {
            blogUserVo.setFollowType(-1);
        } else {
            // 判断是否关注
            int i = followRepository.countByUidAndFollowId(currentUid, id);
            blogUserVo.setFollowType(i > 0 ? 1 : 0);
        }
        return blogUserVo;
    }

    @Override
    public ResultData followUser(Integer uid, Integer follow_id) {
        int i = followRepository.countByUidAndFollowId(uid, follow_id);
        if (i == 0) {
            Follow follow = new Follow();
            follow.setUid(uid);
            follow.setFollowId(follow_id);
            followRepository.save(follow);
            if (follow == null) {
                return new ResultData(ResultData.ERROR_SAVE, "关注失败");
            }
            this.setUserFollowAndFansNum(uid, follow_id, 1);
        }
        return new ResultData("关注成功");
    }

    @Override
    @Transactional
    public ResultData cancelFollowUser(Integer uid, Integer follow_id) {
        int i = followRepository.countByUidAndFollowId(uid, follow_id);
        if (i == 1) {
            i = followRepository.deleteByUidAndFollowId(uid, follow_id);
            if (i == 0) {
                return new ResultData(ResultData.ERROR_SAVE, "取消关注失败");
            }
            this.setUserFollowAndFansNum(uid, follow_id, -1);
        }
        return new ResultData("取消关注成功");
    }

    @Override
    public Map getFollowUser(Integer uid, Pageable pageable) {
        Page<Object[]> objects = followRepository.getFollowUser(uid, pageable);
        HashMap hm = new HashMap();
        if (objects != null) {
            List<FollowUserTo> followUserToList = ConvertDataToVoUtil.convert(objects.getContent(), FollowUserTo.class);
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("followUserList", followUserToList);
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("followUserList", new ArrayList<FollowUserTo>());
        }
        return hm;
    }

    @Override
    public Map getFansUser(Integer uid, Pageable pageable) {
        Page<Object[]> objects = followRepository.getFansUser(uid, pageable);
        HashMap hm = new HashMap();
        if (objects != null) {
            List<FollowUserTo> followUserToList = ConvertDataToVoUtil.convert(objects.getContent(), FollowUserTo.class);
            for (FollowUserTo followUserTo : followUserToList) {
                int i = followRepository.countByUidAndFollowId(uid, followUserTo.getId());
                followUserTo.setFollowType(i > 0 ? 1 : 0);
            }
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("followUserList", followUserToList);
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("followUserList", new ArrayList<FollowUserTo>());
        }
        return hm;
    }

    @Override
    public void setUserFollowAndFansNum(Integer uid, Integer follow_id, Integer num) {
        // 修改我的关注 + 1
        User user = userRepository.findById(uid).orElse(null);
        if (user != null) {
            user.setFollowNum(user.getFollowNum() + num);
            userRepository.save(user);
        }
        // 修改我关注的人的粉丝 + 1
        user = userRepository.findById(follow_id).orElse(null);
        if (user != null) {
            user.setFansNum(user.getFansNum() + num);
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getPopularUser(Integer size) {
        return userRepository.findPopularUser(size);
    }

    @Override
    public Map searchUser(String nickname, Pageable pageable) {
        Page<User> objects = userRepository.findAllByNicknameLikeAndStatus(nickname, (byte)1, pageable);
        System.out.println(objects);
        HashMap hm = new HashMap();
        if (objects != null) {
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("userList", objects.getContent());
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("userList", new ArrayList<User>());
        }
        return hm;
    }
}
