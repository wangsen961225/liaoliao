/*为用户信息表增加字段 2017-12-12*/
alter table ll_user_info  add  management_money double(11);

/*
 * 理财产品表 
 *2017-12-12
 */
CREATE TABLE `ll_money_management` (
  `id` varchar(32) NOT NULL,
  `name` varchar(15) NOT NULL COMMENT '产品名称',
  `startdate` date DEFAULT NULL COMMENT '开始时间',
  `enddate` date DEFAULT NULL COMMENT '结束时间',
  `minamount` int(11) DEFAULT NULL COMMENT '起投金额',
  `maxamount` int(11) DEFAULT NULL COMMENT '最大金额',
  `updatedate` date DEFAULT NULL COMMENT '更新时间',
  `interest_rate` varchar(15) DEFAULT NULL COMMENT '利率',
  `type` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 * 用户购买产品记录表
 * 2017-12-12
 * 
 * */
CREATE TABLE `ll_user_money_management` (
  `id` varchar(36) NOT NULL,
  `userid` int(11) NOT NULL,-- 用户ID
  `moneyid` varchar(36) NOT NULL,-- 理财产品ID
  `money` double(10,0) NOT NULL,-- 购买金额
  `buy_date` datetime NOT NULL, -- 购买时间
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*
 * 用户打赏记录表
 * 2017-12-16
 * 
 * */
CREATE TABLE `ll_play_history` (
  `id` varchar(32) NOT NULL,
  `reder_id` int(11) NOT NULL COMMENT '读者ID',
  `author_id` int(11) NOT NULL COMMENT '作者ID',
  `article_id` int(11) NOT NULL COMMENT '文章或视频ID',
  `money` double(10,0) NOT NULL COMMENT '打赏金额',
  `play_date` date NOT NULL COMMENT '打赏时间',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 * 用户浏览视频和文章记录表
 * 2017-12-23
 * 
 * */
	CREATE TABLE `ll_read_history` (
	  `id` varchar(36) NOT NULL,
	  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
	  `article_id` int(11) NOT NULL COMMENT '文章或视频ID',
	  `type` int(4) NOT NULL COMMENT '0文章   1视频',
	  `add_date` datetime NOT NULL COMMENT '阅读时间',
	  
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	/**
	 * 2018/1/4 
	 */
	ALTER table ll_video add play_money_sum Double (11,1);
	ALTER table ll_play_history add type int(4);
	update  ll_ban_user set user_status='0' where breakout_id='10044' ;
/*-----------------------------------------------------------------------------------------------------*/
	/**
	 * 2018-1-13
	 */
	ALTER table ll_article add play_money_sum Double (11,1) default 0.0;
	/*
	 * 2018-1-18
	 */
	ALTER table ll_weinxin_pay_log add ready_money Double (11,1) default 0.0;