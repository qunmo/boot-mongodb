package sjz.sgy.lb.util;

/**
 * 常量管理
 * @author geyankai
 *
 */
public class ConsoleConstant {
	/** 密码校验规则 */
	public static final String FORMAT_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
	/** 手机号校验规则 */
	public static final String FORMAT_MOBILE = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
	/** 邮箱校验规则 */
	public static final String FORMAT_EMAIL = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
	/** 角色名称规则 */
	public static final String FORMAT_ROLE_NAME = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
	
	/** salt生成随机数的长度 */
	public static final int PASSWORD_SALT_LENGTH = 20; 
	
	
	/** 后台-用户身份-管理员*/
	public static final int SYS_IDENTITY = 0;
	/** 后台-用户身份-店铺商家*/
	public static final int STORE_IDENTITY = 2;
	/** 后台-用户身份-供应商*/
	public static final int SUPPLIER_IDENTITY = 1;
	
	
	/** 后台-登陆-短信验证码失效时间(分钟)*/
	public static final int MOBILE_CODE_LOST_TIME = 5;
	/** 后台-登陆-账户状态-禁用*/
	public static final int USER_STATUS_DISABLE = 0;
	/** 后台-登陆-账户状态-禁用*/
	public static final int USER_STATUS_ENABLE = 1;
	
	/** 删除状态  未删除*/
	public static final int DEL_STATE_NO = 1;
	/** 删除状态  已删除*/
	public static final int DEL_STATE_ALREAD = 2;
	
	
}
