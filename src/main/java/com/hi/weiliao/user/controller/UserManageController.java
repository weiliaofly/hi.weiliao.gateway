package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@RequestMapping(value = "/usermanage")
public class UserManageController extends BaseController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ReturnObject queryList(@RequestParam(required = false) String phone,
                                  @RequestParam Integer page_no,
                                  @RequestParam @Size(min = 10, max = 100, message = "page_size只能为10-100") Integer page_size) {
        return new ReturnObject(ReturnCode.SUCCESS, userManageService.query(phone, page_no, page_size));
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
