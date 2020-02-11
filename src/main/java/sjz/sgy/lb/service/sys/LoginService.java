package sjz.sgy.lb.service.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjz.sgy.lb.dao.sys.LoginDao;
import sjz.sgy.lb.exception.RRException;
import sjz.sgy.lb.interceptor.AppFrequent;
import sjz.sgy.lb.interceptor.AppFrequentContext;
import sjz.sgy.lb.util.Md5Util;
import sjz.sgy.lb.util.jwt.JwtUtils;
import sjz.sgy.lb.util.jwt.LoginConstant;
import sjz.sgy.lb.util.jwt.TokenConstant;

/**
* @author 郄俊杰
* @version 创建时间：2020年2月5日 下午3:10:52
* 类说明
*/
@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private InterceptService interceptService;
	
	public Map<String, Object> login(Map<String, Object> sysUser){
		Map<String, Object> user=loginDao.getSysUser(sysUser);
		if(user==null){
			throw new RRException("账号不正确！");
		}else{
			if(!user.get("identity").toString().equals(LoginConstant.CONSOLE_TYPE)){
				throw new RRException("您的身份不是后台管理员!");
			}
			if(!user.get("password").equals(Md5Util.strMd5(sysUser.get("password").toString()))){
				throw new RRException("密码不正确！");
			}
		}
		//生成token  1.access_token 2.refresh_token
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		// 账号ID,账号,用户Id,用户名称
		String subject = user.get("sysUserId") + "," + LoginConstant.CONSOLE_TYPE + "," + user.get("userName") + ","
						+ user.get("password");
	    // 使用token封装必要信息  储存token
		String accessToken = JwtUtils.createJWT(uuid, subject, TokenConstant.JWT_TTL);
		String refreshToken = JwtUtils.createJWT(uuid2, subject, TokenConstant.long_JWT_TTL);
		user.put("uuid", uuid);
		user.put("token", accessToken);
		user.put("otherId", uuid2);
		interceptService.addFrequentMessage(user);
		user.put("otherId", uuid);
		user.put("token", refreshToken);
		user.put("uuid", uuid2);
		interceptService.addFrequentMessage(user);
		Map<String, Object> result=new HashMap<>();
		result.put("accessToken", accessToken);
		result.put("refreshToken",refreshToken);
		result.put("userName", user.get("userName"));
		return result;
	}
	public Map<String, Object> loginFront(Map<String, Object> memberUser){
		Map<String, Object> user=loginDao.getMember(memberUser);
		if(user==null){
			throw new RRException("账号不正确！");
		}else{
			if(!user.get("password").equals(Md5Util.strMd5(memberUser.get("password").toString()))){
				throw new RRException("密码不正确！");
			}
		}
		//生成token  1.access_token 2.refresh_token
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		// 账号ID,账号,用户Id,用户名称
		String subject = user.get("memberId") + "," + LoginConstant.FRONT_TYPE+ "," + user.get("accountNumber") + ","
						+ user.get("password");
	    // 使用token封装必要信息 储存token
		String accessToken = JwtUtils.createJWT(uuid, subject, TokenConstant.JWT_TTL);
		String refreshToken = JwtUtils.createJWT(uuid2, subject, TokenConstant.long_JWT_TTL);
		user.put("uuid", uuid);
		user.put("token", accessToken);
		user.put("otherId", uuid2);
		interceptService.addFrequentMessage(user);
		user.put("uuid", uuid2);
		user.put("token", refreshToken);
		user.put("otherId", uuid);
		interceptService.addFrequentMessage(user);
		Map<String, Object> result=new HashMap<>();
		result.put("accessToken", accessToken);
		result.put("refreshToken",refreshToken);
		result.put("wechatNickname", user.get("wechatNickname"));
		result.put("wechatIcon", user.get("wechatIcon"));
		result.put("memberId", user.get("memberId"));
		return result;
	}
	
	/**
	 * token的更换
	 * 
	 * @param pd
	 * @return
	 */
	public String changeToken(Map<String, Object> pd) {
	    Map<String, Object> map=new HashMap<String, Object>();
	    Map<String, Object> sysUser=new HashMap<String, Object>();
		if (pd == null) {
			throw new RRException("refreshToken不正确，重新登陆");
		}
		String uuid="";
		AppFrequent appFrequent=new AppFrequent();
		try {
			//refreshToken 得uuid
			uuid = JwtUtils.parseJWTGetCode(pd.get("refreshToken").toString());
			appFrequent=AppFrequentContext.getInstance().getAppFrequent(uuid,"2");
			boolean flag=AppFrequentContext.getInstance().judgeToken(pd.get("refreshToken").toString(), appFrequent);
			if(!flag) {
				throw new RRException("refreshToken已经失效，请重新登陆");
			}
			String sub=JwtUtils.parseJWTGetSubject(pd.get("refreshToken").toString());
			String[] user=sub.split(",");
			if(LoginConstant.CONSOLE_TYPE.equals(user[1].toString())){
				map.put("type", LoginConstant.CONSOLE_TYPE);
				map.put("userId", user[0]);
				sysUser=loginDao.getSysUser(map);
			}else{
				//前端用户
				map.put("type", LoginConstant.FRONT_TYPE);
				map.put("memberId", user[0]);
				sysUser=loginDao.getMember(map);
			}
		} catch (Exception e) {
			throw new RRException("用户信息校验失败请重新登录",502);
		}
		String subject="";
		if(LoginConstant.CONSOLE_TYPE.equals(map.get("type").toString())){
			subject = sysUser.get("sysUserId") + "," + LoginConstant.CONSOLE_TYPE + "," + sysUser.get("userName") + ","
					+ sysUser.get("password");
		}else{
			//前端用户
			subject = sysUser.get("memberId") + "," + LoginConstant.FRONT_TYPE+ "," + sysUser.get("accountNumber") + ","
					+ sysUser.get("password");
		}
		//access Token uuid
		String uuid2 = UUID.randomUUID().toString();
		
		// 使用token封装必要信息
		String accessToken = JwtUtils.createJWT(uuid2, subject, TokenConstant.JWT_TTL);
		//如果之前得token没有清除则清除
		AppFrequent old=AppFrequentContext.getInstance().getAppFrequentMap().get(appFrequent.getOtherId());
		if(old!=null){
			AppFrequentContext.getInstance().delAppFrequent(old);
		}
		Map<String, Object> message=new HashMap<>();
		message.put("uuid", uuid2);
		message.put("token", accessToken);
		message.put("otherId", uuid);
		interceptService.addFrequentMessage(message);
		//更新长token得过期时间
		appFrequent.setVisitTime(new Date());
		appFrequent.setOtherId(uuid2);
		
		return accessToken;
	}
    
	/**
	 * @Title: logout
	 * @Description: 退出清除token 
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月7日 上午9:25:33
	 */
	public void logout(){
		interceptService.delFrequentMessage();
	}
}
