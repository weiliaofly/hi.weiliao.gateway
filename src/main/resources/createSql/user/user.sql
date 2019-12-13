DROP TABLE IF EXISTS USER_VERIFY_CODE;
CREATE TABLE USER_VERIFY_CODE (
  PHONE              VARCHAR(11)     NOT NULL COMMENT '手机号',
  MSG_TYPE           TINYINT(1)      NOT NULL DEFAULT 1 COMMENT '验证码类型：1-注册，2-登录，3-重置密码，4-修改信息',
  VERIFY_CODE        VARCHAR(6)     NULL DEFAULT '' COMMENT '验证码',
  CREATE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY USER_VERIFY_CODE(PHONE, MSG_TYPE)
) COMMENT '用户验证码表';

DROP TABLE IF EXISTS USER_AUTH;
CREATE TABLE USER_AUTH (
  ID                 INTEGER         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PHONE              VARCHAR(11)     NULL COMMENT '手机号',
  PASSWORD           VARCHAR(50)     NULL DEFAULT '' COMMENT '密码',
  PW_TRY_TIMES       TINYINT(1)      NULL DEFAULT 0 COMMENT '密码尝试次数',
  WX_OPENID			 VARCHAR(200)    NULL DEFAULT '' COMMENT '微信的openid',
  SESSION			 VARCHAR(50)     NULL DEFAULT '' COMMENT '用户会话',
  CREATE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  REVISE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (ID),
  UNIQUE KEY USER_AUTH(PHONE)
) COMMENT '用户表';
ALTER TABLE USER_AUTH AUTO_INCREMENT = 190311;

DROP TABLE IF EXISTS USER_INFO;
CREATE TABLE USER_INFO (
  USER_ID            INTEGER         NOT NULL COMMENT '用户ID',
  NAME               VARCHAR(20)     NULL DEFAULT '' COMMENT '昵称',
  HEAD_ICON			 VARCHAR(200)    NULL DEFAULT '' COMMENT '头像',
  BACKGROUND         VARCHAR(200)    NULL DEFAULT '' COMMENT '背景',
  PERSONAL_SIGN      VARCHAR(200)    NULL DEFAULT '' COMMENT '个性签名',
  SEX                TINYINT(1)      NULL DEFAULT 1 COMMENT '性别:0-女，1-男',
  BIRTHDAY           TIMESTAMP       NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生日',
  PROVINCE           VARCHAR(20)     NULL DEFAULT '' COMMENT '省份',
  CITY               VARCHAR(20)     NULL DEFAULT '' COMMENT '城市',
  COIN               DECIMAL(20,0)     NOT NULL DEFAULT 50 COMMENT '金币数',
  REVISE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (USER_ID),
  UNIQUE KEY NAME(NAME)
) COMMENT '用户信息表';

DROP TABLE IF EXISTS USER_FOLLOW;
CREATE TABLE USER_FOLLOW (
  USER_ID            INTEGER         NOT NULL COMMENT '粉丝用户ID',
  FOLLOW_ID          INTEGER         NOT NULL COMMENT '被关注用户ID',
  CREATE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX USER_ID(USER_ID),
  INDEX FOLLOW_ID(FOLLOW_ID),
  UNIQUE KEY USER_FOLLOW(USER_ID, FOLLOW_ID)
) COMMENT '用户关注粉丝表';

DROP TABLE IF EXISTS GLOBAL_CONFIG;
CREATE TABLE GLOBAL_CONFIG (
  CONFIG_KEY            VARCHAR(200)          NOT NULL COMMENT '配置名',
  CONFIG_VALUE          VARCHAR(200)          NOT NULL COMMENT '配置值',
  COMMENT               VARCHAR(200)          NOT NULL COMMENT '说明',
  CREATE_TIME           TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY CONFIG_KEY(CONFIG_KEY)
) COMMENT '系统参数配置表';

DROP TABLE IF EXISTS SIGN_HISTORY;
CREATE TABLE SIGN_HISTORY (
  USER_ID            INTEGER         NOT NULL COMMENT '用户ID',
  SIGN_TYPE          TINYINT(1)      NULL DEFAULT 1 COMMENT '签到类型:1-每日签到',
  CREATE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX USER_ID(USER_ID)
) COMMENT '签到表';

DROP TABLE IF EXISTS INVITE_HISTORY           ;
CREATE TABLE INVITE_HISTORY (
  USER_ID            INTEGER         NOT NULL COMMENT '用户ID',
  INVITE_ID          INTEGER         NOT NULL COMMENT '邀请注册的用户ID',
  CREATE_TIME        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '邀请时间',
  INDEX USER_ID(USER_ID),
  UNIQUE KEY INVITE_ID(INVITE_ID)
) COMMENT '邀请表';

INSERT INTO GLOBAL_CONFIG (CONFIG_KEY, CONFIG_VALUE, COMMENT)
VALUES
('SIGN_IN', '5', '每日签到赠送金币'),
('INVITE', '30', '邀请新人注册赠送金币');
