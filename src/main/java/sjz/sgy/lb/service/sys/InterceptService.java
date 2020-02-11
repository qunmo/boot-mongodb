package sjz.sgy.lb.service.sys;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sjz.sgy.lb.exception.RRException;
import sjz.sgy.lb.interceptor.AppFrequent;
import sjz.sgy.lb.interceptor.AppFrequentContext;
import sjz.sgy.lb.util.jwt.JwtUtils;
/**
 * 登录拦截信息的封装
 * @author liyue
 *
 */
@Service
public class InterceptService {
	private AppFrequentContext appFrequentContext = AppFrequentContext.getInstance(); 
	
	
	/**
	 *登录信息
	 *标志码code
	 * @return
	 */
	public void addFrequentMessage(Map<String, Object> pd) {
		AppFrequent appFrequent=new AppFrequent();
		appFrequent.setId(pd.get("uuid").toString());
		if(pd.get("type")!=null) {
			//type进行信息存储
			appFrequent.setType(Integer.parseInt(pd.get("type").toString()));
		}
		appFrequent.setVisitTime(new Date());
		//跟权限有关
		/*List<Integer> positions=new ArrayList<Integer>();
		List<String> permissionList=new ArrayList<String>();
		if(LoginConstant.CONSOLE_TYPE==pd.get("type")) {
			int flag=5;
			while(flag>0) {
				try {
					 positions=userFeignService.findPositionByUser(pd);
					 permissionList=commonFeignService.findPrivilegeUrlsByPositionIds(JSON.toJSONString(positions));
					 flag=0;
				}catch(Exception ex) {
					flag--;
				}
			}
		}else {
			appFrequent.setRefreshToken(pd.getString("refreshToken"));
		}
		appFrequent.put("permissionList", JSON.toJSONString(permissionList));*/
		appFrequent.setToken(pd.get("token").toString());
		appFrequent.setOtherId(pd.get("otherId").toString());
		appFrequentContext.addAppFrequent(appFrequent);
	}
	
	public int appRegister() {
		return 0;
	}
	
	/**
	 * 删除信息
	 * @param appFrequent
	 */
	public void delFrequentMessage() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			String token = JwtUtils.getToken(request);
			String code=JwtUtils.parseJWTGetCode(token);
			AppFrequent appFrequent=appFrequentContext.getAppFrequent(code,"1");
			String code2=appFrequent.getOtherId();
			appFrequentContext.delAppFrequent(appFrequent);
			AppFrequent appFrequent2=appFrequentContext.getAppFrequent(code2,"2");
			appFrequentContext.delAppFrequent(appFrequent2);
		}catch(Exception ex) {
			throw new RRException("登录已失效，不用重复的点击退出");
		}
		
	}
	/**
	 * 获取token
	 * @param appFrequent
	 */
	public String getToken() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			String token = JwtUtils.getToken(request);
			return token;
		}catch(Exception ex) {
			throw new RRException("获取token失败");
		}
		
	}
	/**
	 * 获取userId
	 * @param appFrequent
	 */
	public String getUserId() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			String token = JwtUtils.getToken(request);
			if(null!=token&&!("").equals(token)){
				String subject=JwtUtils.parseJWTGetSubject(token);
				String[] sub=subject.split(",");
				String userId=sub[0];
				return userId;
			}else{
				throw new RRException("获取userId失败");
			}
		}catch(Exception ex) {
			throw new RRException("获取userId失败");
		}
		
	}
	
}
