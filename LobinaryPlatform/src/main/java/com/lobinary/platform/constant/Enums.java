package com.lobinary.platform.constant;

public enum Enums {
	
	/**
	 * 时间格式 2016-06-06T06:06:06.000Z
	 */
	date_type_2016_06_06T06_06_06_000Z,
	/**
	 * 时间格式2014/05/13 16:00:37
	 */
	date_type_2016_06_06_06_06_06,
	
	/**
	 * 格式-未知格式
	 */
	type_unknow_type,
	/**
	 * 格式-字符串
	 */
	type_string,
	/**
	 * 格式-整型
	 */
	type_int,
	/**
	 * 格式-长整形
	 */
	type_long,
	/**
	 * 格式-浮点型
	 */
	type_float,
	/**
	 * 格式-双浮点型
	 */
	type_double,
	/**
	 * 格式-日期
	 */
	type_date,
	/**
	 * 格式-时间
	 */
	type_time,
	/**
	 * 格式-对象
	 * 暂不支持转成object形式
	 */
	@Deprecated
	type_object,
	
	/**
	 * 对象-叉车信息 
	 */
	model_forklift,
	/**
	 * 对象-客户信息
	 */
	model_user,
	/**
	 * 对象-保养信息
	 */
	model_maintain,
	/**
	 * 对象-维修信息
	 */
	model_repair,
	/**
	 * 对象-零件信息
	 */
	model_part,
	/**
	 * 对象-未知的对象模型
	 */
	model_unknow_model,
	
	/**
	 * 验证码-校验器(判断验证码是否正确,没有其他需求)
	 */
	captcha_validator,
	/**
	 * 验证码-登录校验(判断验证码是否正确,如果正确,继续验证密码,如果错误,返回提示)
	 */
	captcha_login_validate,
	/**
	 * 验证码-注册校验(判断验证码是否正确,如果正确,继续注册,如果错误,返回提示)
	 */
	captcha_register_validate,
	/**
	 * 验证码-不是和验证码相关的url
	 */
	captcha_is_not_captcha_url,

	
	/**
	 * 登录信息提示-登录密码错误
	 */
	loginMessage_password_is_error,
	/**
	 * 登录信息提示-登陆验证码正确
	 */
	loginMessage_captcha_is_right,
	/**
	 * 登陆信息提示-登录验证码错误
	 */
	loginMessage_captcha_is_error,
	/**
	 * 登录信息提示-登录验证码过期
	 */
	loginMessage_captcha_is_expired,
	/**
	 * 登录信息提示-匿名访问被禁止
	 */
	loginMessage_anonymous_forbidden,
	/**
	 * 登录信息提示-登陆已经过期
	 */
	loginMessage_login_is_expired,
	/**
	 * 登录信息提示-登陆成功
	 */
	loginMessage_login_is_success,
	/**
	 * 登录信息提示-已经登录
	 */
	loginMessage_is_alreadyLogin,
	
	
	/**
	 * 增加
	 * (暂不支持增加功能)
	 */
	@Deprecated
	execute_type_add,
	/**
	 * HQL生成处理格式-删除格式
	 */
	execute_type_delete,
	/**
	 * HQL生成处理格式-更新格式
	 */
	execute_type_update,
	/**
	 * HQL生成处理格式-查找格式
	 */
	execute_type_select,
	/**
	 * HQL生成处理格式-计数格式（计算总结果集列数）
	 */
	execute_type_count,
	
	
	/**
	 * 角色-匿名人员
	 */
	ROLE_ANONYMOUS,
	
	/**
	 * 角色-用户
	 */
	ROLE_USER,
	
	/**
	 * 角色-管理员
	 */
	ROLE_MANAGER,
	
	/**
	 * 角色-超级管理员
	 */
	ROLE_ADMIN,
	
	/**
	 * 角色-顶级管理员
	 */
	ROLE_LOBINARY
	
}
