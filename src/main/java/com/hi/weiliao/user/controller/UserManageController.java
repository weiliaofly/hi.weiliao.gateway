package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usermanage")
public class UserManageController extends BaseController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ReturnObject query() {
        return new ReturnObject(ReturnCode.SUCCESS, userManageService.query());
    }

    @RequestMapping(value = "/{phone}", method = RequestMethod.DELETE)
    public ReturnObject query(@PathVariable("phone") String phone) {
        if (!phone.matches("^1\\d{10}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "手机号错误");
        }
        userManageService.deleteByPhone(phone);
        return new ReturnObject(ReturnCode.SUCCESS);
    }
}
