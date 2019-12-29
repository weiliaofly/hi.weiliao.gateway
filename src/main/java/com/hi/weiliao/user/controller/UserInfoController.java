package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.base.utils.TimeUtils;
import com.hi.weiliao.user.UserContext;
import com.hi.weiliao.user.bean.InviteHistory;
import com.hi.weiliao.user.bean.SignHistory;
import com.hi.weiliao.user.bean.UserInfo;
import com.hi.weiliao.user.service.InviteHistoryService;
import com.hi.weiliao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/userinfo")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private InviteHistoryService inviteHistoryService;

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

    /**
     * 签到领金币
     * @return
     */
    @RequestMapping(value = "/sign_in", method = RequestMethod.POST)
    public ReturnObject signIn() {
        Integer userId = userContext.getUserIdAndCheck();
        userInfoService.signIn(userId);
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 查询签到历史记录
     * @return
     */
    @RequestMapping(value = "/sign_history", method = RequestMethod.GET)
    public ReturnObject signHistory(@Valid @Pattern(regexp = TimeUtils.REG_YYYY_MM_DD, message = "日期格式不正确") @RequestParam(required = false) String from_on,
                                    @Valid @Pattern(regexp = TimeUtils.REG_YYYY_MM_DD, message = "日期格式不正确") @RequestParam(required = false) String to_on) {
        Integer userId = userContext.getUserIdAndCheck();
        if (StringUtils.isEmpty(from_on) || StringUtils.isEmpty(to_on)) {
            from_on = TimeUtils.getCurrentYYYYMMDD();
            to_on = TimeUtils.getCurrentYYYYMMDD();
        }
        List<SignHistory> result = userInfoService.getSignHistory(userId, from_on, to_on);
        return new ReturnObject(ReturnCode.SUCCESS, result);
    }

    /**
     * 查询邀请记录
     * @return
     */
    @RequestMapping(value = "/invite_history", method = RequestMethod.GET)
    public ReturnObject inviteHistory() {
        Integer userId = userContext.getUserIdAndCheck();
        List<InviteHistory> inviteHistories = inviteHistoryService.getInviteHistory(userId, null);
        Map<String, Object> result = new HashMap();
        BigDecimal inviteCoin = BigDecimal.ZERO;
        for (InviteHistory history : inviteHistories) {
            inviteCoin = inviteCoin.add(history.getAddCoin());
        }
        int inviteNum = CollectionUtils.isEmpty(inviteHistories) ? 0 : inviteHistories.size();
        result.put("invite_num", inviteNum);
        result.put("invite_coin", inviteCoin);
        return new ReturnObject(ReturnCode.SUCCESS, result);
    }
}
