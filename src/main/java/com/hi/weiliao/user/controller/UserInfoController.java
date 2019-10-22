package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.UserContext;
import com.hi.weiliao.user.bean.UserInfo;
import com.hi.weiliao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/userinfo")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserContext userContext;

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ReturnObject get() {
        int userId = userContext.getUserIdAndCheck();
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if (null == userInfo) {
            return new ReturnObject(ReturnCode.NO_CONTENT);
        }
        return new ReturnObject(ReturnCode.SUCCESS, userInfo);
    }

    /**
     * 获取指定用户信息
     *
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ReturnObject getOther(@RequestParam Integer user_id) {
        UserInfo userInfo = userInfoService.getUserInfoById(user_id);
        if (null == userInfo) {
            return new ReturnObject(ReturnCode.NO_CONTENT);
        }
        return new ReturnObject(ReturnCode.SUCCESS, userInfo);
    }

    /**
     * 修改当前用户信息
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ReturnObject get(@RequestBody UserInfo userInfo) {
        int userId = userContext.getUserIdAndCheck();
        userInfo.setUserId(userId);
        int count = userInfoService.updateUserInfo(userInfo);
        if (count == 0) {
            return new ReturnObject(ReturnCode.NO_CHANGE);
        }
        return new ReturnObject(ReturnCode.SUCCESS);
    }
}
