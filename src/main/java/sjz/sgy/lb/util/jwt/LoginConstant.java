package sjz.sgy.lb.util.jwt;

public class LoginConstant {
	/**
	 * token
	 */
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
	public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN
	
	public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

	public static final long JWT_TTL = 60 * 60 * 1000; // token有效时间
	public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d"; // 密匙
	//资源类型
	public static final String PERMISSION="permissionList";
	//系统用户
	public static final String ACTIVEUSER="ACTIVEUSER";
	//token在头文件中的名称
	public static final String TOKEN="access_token";
	
	//APP常登陆的时长
//	public static final long APP_REMEMBER_Time=60 * 24 * 60 * 60 ;
	//后台登录时长
	public static final long SESSION_REMEMBER_Time= 60 * 60 * 1000;
	
//	public static final long ACCESS_TOKEN_TIME=7*60*60;
//	public static final long REFRESH_TOKEN_TIME=15*24*60*60 ;
	public static final long ACCESS_TOKEN_TIME=2 * 24 * 60 * 60 ;
	public static final long REFRESH_TOKEN_TIME=15 * 24 * 60 * 60  ;
	
	//内部接口
	public static final Integer SYSTEM_TYPE=0;
	//后台管理的拦截类型
	public static final Integer CONSOLE_TYPE=1;
	//前端类型
	public static final Integer FRONT_TYPE=2;
	
	
	
	//personIcon","danceIcon","bannerPhoto","apkFile","levelIcon","exchangeReward","activity","vote
	
	//APP的拦截路径
	public static final String APP="app";
	//web端的拦截路径
	public static final String CONSOLE="console";
	//图片一些静态文件的保存路径
	public static final String COMMON="^\\/common\\/(personIcon|danceIcon|bannerPhoto|apkFile|levelIcon|exchangeReward|activity|vote|signUp|reward|video)\\/(.*)$";

}
