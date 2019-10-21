package com.hi.weiliao.user.controller;

import com.github.pagehelper.PageInfo;
import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.UserContext;
import com.hi.weiliao.user.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.Map;

@RestController
@RequestMapping(value = "/userfollow")
public class UserFollowController extends BaseController {

    @Autowired
    private UserFollowService userFollowService;


    @Autowired
    private UserContext userContext;

    /**
     * 关注用户
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ReturnObject follow(@RequestBody Map<String, String> params) {
        String followId = params.get("follow_id");
        if (StringUtils.isEmpty(followId) || !followId.matches("^\\d$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        int result = userFollowService.follow(userContext.getUserId(), Integer.valueOf(followId));
        if (result == 0) {
            return new ReturnObject(ReturnCode.NO_CHANGE);
        }
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 取消关注用户
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/cancel_follow", method = RequestMethod.POST)
    public ReturnObject cancelFollow(@RequestBody Map<String, String> params) {
        String followId = params.get("follow_id");
        if (StringUtils.isEmpty(followId) || !followId.matches("^\\d$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        int result = userFollowService.cancelFollow(userContext.getUserId(), Integer.valueOf(followId));
        if (result == 0) {
            return new ReturnObject(ReturnCode.NO_CHANGE);
        }
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 获取关注与被关注总数
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ReturnObject count(@RequestParam Integer user_id) {
        Map result = userFollowService.countFollow(user_id);
        return new ReturnObject(ReturnCode.SUCCESS, result);
    }

    /**
     * 查询关注列表
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/query_follow", method = RequestMethod.GET)
    public ReturnObject queryFollow(@RequestParam Integer user_id,
                                    @RequestParam @Size(min = 1, max = 1000, message = "page_no只能为1-1000") Integer page_no,
                                    @RequestParam @Size(min = 10, max = 100, message = "page_size只能为10-100") Integer page_size) {

        PageInfo result = userFollowService.queryFollow(user_id, page_no, page_size);
        return new ReturnObject(ReturnCode.SUCCESS, result);
    }

    /**
     * 查询粉丝列表
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/query_fans", method = RequestMethod.GET)
    public ReturnObject queryFans(@RequestParam Integer user_id,
                                  @RequestParam @Size(min = 1, max = 1000, message = "page_no只能为1-1000") Integer page_no,
                                  @RequestParam @Size(min = 10, max = 100, message = "page_size只能为10-100") Integer page_size) {

        PageInfo result = userFollowService.queryFans(user_id, page_no, page_size);
        return new ReturnObject(ReturnCode.SUCCESS, result);
    }
}
