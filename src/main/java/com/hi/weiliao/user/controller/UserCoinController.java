package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.UserContext;
import com.hi.weiliao.user.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@Validated
@RestController
@RequestMapping(value = "/usercoin")
public class UserCoinController extends BaseController {

    @Autowired
    private UserContext userContext;

    @Autowired
    private UserCoinService userCoinService;

    /**
     * 消费金币
     * @param params
     * @return
     */
    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    public ReturnObject consume(@RequestBody Map<String, String> params) {
        Integer userId = userContext.getUserIdAndCheck();
        String coin = params.get("coin");
        if (StringUtils.isEmpty(coin) || !coin.matches("^\\d$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        userCoinService.subCoin(userId, new BigDecimal(coin));
        return new ReturnObject(ReturnCode.SUCCESS);
    }
}
