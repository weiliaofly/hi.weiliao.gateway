package com.hi.weiliao.user.service;

import java.math.BigDecimal;

public interface UserCoinService {

    boolean subCoin(int userId, BigDecimal coin);
}
