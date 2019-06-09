package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.Area;
import cc.lastone.hiblog.domain.Industry;
import cc.lastone.hiblog.domain.User;
import cc.lastone.hiblog.service.AreaService;
import cc.lastone.hiblog.service.ArticleService;
import cc.lastone.hiblog.service.IndustryService;
import cc.lastone.hiblog.service.UserService;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import cc.lastone.hiblog.vo.EditUserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping({"/myInfo"})
public class MyInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private IndustryService industryService;

    @Autowired
    private ArticleService articleService;

    /**
     * 用户首页
     */
    @RequestMapping({""})
    public String index(Model model) {
        //System.out.println(userService.getUserInfo(UserUtil.getUserId()));
        model.addAttribute("editUserInfoVo", userService.getUserInfo(UserUtil.getUserId()));
        model.addAttribute("myInfoRotuer", "index");
        return "myInfo/index";
    }

    /**
     * 我的关注
     */
    @RequestMapping({"/follow"})
    public String follow(@RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                         Model model) {
        // 添加分页
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size);
        // 获得博客数据和分页信息
        model.addAttribute("followUserPaging", userService.getFollowUser(UserUtil.getUserId(), pageRequest));
        model.addAttribute("myInfoRotuer", "follow");
        return "myInfo/follow";
    }

    /**
     * 我的粉丝
     */
    @RequestMapping({"/fans"})
    public String fans(@RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = "10") Integer size,
                       Model model) {
        // 添加分页
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size);
        // 获得博客数据和分页信息
        model.addAttribute("fansUserPaging", userService.getFansUser(UserUtil.getUserId(), pageRequest));
        model.addAttribute("myInfoRotuer", "fans");
        return "myInfo/fans";
    }

    /**
     * 关注或取消关注
     */
    @RequestMapping({"/setFollow"})
    @ResponseBody
    public ResultData setFollow(Integer uid, Integer type) {
        if (uid == null || type == null) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        // 关注
        if (type > 0) {
            return userService.followUser(UserUtil.getUserId(), uid);
        } else {
            // 取消关注
            return userService.cancelFollowUser(UserUtil.getUserId(), uid);
        }
    }

    /**
     * 我的收藏
     */
    @RequestMapping({"/collect"})
    public String collect(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          Model model) {
        // 添加分页
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size);
        // 获得博客数据和分页信息
        model.addAttribute("collectArticlePaging", articleService.getCollectArticle(UserUtil.getUserId(), pageRequest));
        model.addAttribute("myInfoRotuer", "collect");
        return "myInfo/collect";
    }

    /**
     * 收藏博客或取消收藏
     */
    @RequestMapping({"/setCollect"})
    @ResponseBody
    public ResultData setCollect(Integer articleId, Integer type) {
        if (articleId == null || type == null) {
            return new ResultData(ResultData.ERROR_PARAM, "参数错误");
        }
        // 收藏
        if (type > 0) {
            return articleService.collectArticle(UserUtil.getUserId(), articleId);
        } else {
            // 取消收藏
            return articleService.cancelCollectArticle(UserUtil.getUserId(), articleId);
        }
    }

    /**
     * 修改头像
     */
    @RequestMapping({"/saveAvatar"})
    @ResponseBody
    public ResultData saveAvatar(String avatar) {
        if (avatar.isEmpty()) {
            return new ResultData(ResultData.ERROR_PARAM, "请上传图片");
        }
        User user = new User();
        user.setId(UserUtil.getUserId());
        user.setAvatar(avatar);
        user = userService.save(user);
        if (user == null) {
            return new ResultData(ResultData.ERROR_SAVE, "保存头像失败");
        }
        // 修改sessionUser
        UserUtil.setSessionUser(user);
        return new ResultData("修改头像成功");
    }

    /**
     * 修改个人信息
     */
    @GetMapping({"/editUser"})
    @ResponseBody
    public ResultData editUser() {
        // 获得职位，区域
        User user = UserUtil.getSessionUser();
        ArrayList<Area> provinceList = (ArrayList<Area>) areaService.getListByParentId(0);
        ArrayList<Area> cityList = user.getProvinceId() > 0 ? (ArrayList<Area>) areaService.getListByParentId(user.getProvinceId()) : new ArrayList<Area>();
        ArrayList<Area> districtList = user.getCityId() > 0 ? (ArrayList<Area>) areaService.getListByParentId(user.getCityId()) : new ArrayList<Area>();
        ArrayList<Industry> industryList = (ArrayList<Industry>) industryService.getList();
        HashMap hm = new HashMap();
        hm.put("provinceList", provinceList);
        hm.put("cityList", cityList);
        hm.put("districtList", districtList);
        hm.put("industryList", industryList);
        return new ResultData<HashMap>(hm);
    }

    /**
     * 修改个人信息
     */
    @PostMapping({"/editUser"})
    @ResponseBody
    public ResultData editUser(@Valid EditUserInfoVo editUserInfoVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultData(ResultData.ERROR_PARAM, bindingResult.getFieldError().getDefaultMessage());
        }
        // 修改数据
        User user = new User();
        user.setId(UserUtil.getUserId());
        BeanUtils.copyProperties(editUserInfoVo, user);
        user = userService.save(user);
        if (user == null) {
            return new ResultData(ResultData.ERROR_SAVE, "修改个人信息失败");
        }
        // 修改sessionUser
        UserUtil.setSessionUser(user);
        return new ResultData("修改个人信息成功");
    }

}
