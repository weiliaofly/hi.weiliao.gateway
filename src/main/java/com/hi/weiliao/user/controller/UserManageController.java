package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.service.InviteHistoryService;
import com.hi.weiliao.user.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@RequestMapping(value = "/usermanage")
public class UserManageController extends BaseController {

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private InviteHistoryService inviteHistoryService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ReturnObject queryList(@RequestParam(required = false) String phone,
                                  @RequestParam(required = false) String orderBy,
                                  @RequestParam @Size(min = 1, max = 1000, message = "page_no只能为1-1000") Integer page_no,
                                  @RequestParam @Size(min = 10, max = 100, message = "page_size只能为10-100") Integer page_size) {
        return new ReturnObject(ReturnCode.SUCCESS, userManageService.query(phone, page_no, page_size));
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
    public ReturnObject delete(@PathVariable("userid") Integer userid) {
        int result = userManageService.deleteByUserId(userid);
        if (result == 0) {
            return new ReturnObject(ReturnCode.NO_CHANGE);
        }
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/invite_history", method = RequestMethod.GET)
    public ReturnObject getInviteHistory(@RequestParam(value = "userid", required = false) Integer userid,
                                         @RequestParam(value = "inviteid", required = false) Integer inviteid) {

        return new ReturnObject(ReturnCode.SUCCESS, inviteHistoryService.getInviteHistory(userid, inviteid));
    }
}
