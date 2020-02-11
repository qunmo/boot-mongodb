package sjz.sgy.lb.interceptor;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import sjz.sgy.lb.util.R;
import sjz.sgy.lb.util.jwt.JwtUtils;
import sjz.sgy.lb.util.jwt.TokenConstant;



/**
 * 拦截器 用户权限校验
 * 创建者   科帮网 
 * 创建时间  2017年11月24日
 */
public class SysInterceptor implements HandlerInterceptor {  
    
	private static final Logger logger = LoggerFactory.getLogger(SysInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  
            throws Exception {  
    	//解决跨域
    	crossDomain(request, response);
    	if (handler instanceof HandlerMethod){
    		String authHeader = request.getHeader("access_token");
    		
    		System.out.println("authHeader:  "+authHeader);
        	if (StringUtils.isEmpty(authHeader)) {
        	  logger.info("验证失败");
        	  print(response,R.error(TokenConstant.JWT_ERRCODE_NULL,"签名验证不存在"));
              return false;
            }else{
            	try {
    				String token = JwtUtils.getToken(request);
    				//短期验证
    				String code=JwtUtils.parseJWTGetCode(token);
    				 //验证token的有效性//长期验证code为UUID每次进行信息更换都需要同步进行更新操作
    				 try {
    					 AppFrequent appFrequent=AppFrequentContext.getInstance().getAppFrequent(code,"1");
    				     boolean flag=AppFrequentContext.getInstance().judgeToken(token, appFrequent);
    					 if(!flag) {
    						print(response,R.error(501,"token信息失效，请重新换取"));
    						return false;
    					 }
    					 return true;
    			         //权限
    				 }catch(Exception ex) {
    					 print(response,R.error(501,"token信息失效，请重新换取"));
    					 return false;
    				 }
    			}catch (Exception ex){
    				print(response,R.error(502,"用户信息校验失败请重新登录"));
    				return false;
    				
    			}
            }
		}else{
			return true;
		}
    }  
    public void print(HttpServletResponse response,Object message){
    	try {
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			PrintWriter writer = response.getWriter();
			writer.write(JSONObject.toJSONString(message, SerializerFeature.WriteMapNullValue,
	                SerializerFeature.WriteDateUseDateFormat));
			writer.flush();
			writer.close();
		} catch (IOException e) {
//			e.printStackTrace();
			logger.info(""+e);
		}
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  
                           ModelAndView modelAndView) throws Exception {  
        if(response.getStatus()==500){  
        	print(response,R.error(500,"未知异常"));
        }else if(response.getStatus()==404){  
        	print(response,R.error(404,"no found"));
        }  
    }  
  
    /**  
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，  
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的
     * 返回值为true时才会执行。  
     */  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
    }  


    public void crossDomain(HttpServletRequest request, HttpServletResponse response) {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");//允许的跨域请求方式
		/*每次异步请求都发起预检请求，也就是说，发送两次请求。第一次是浏览器使用OPTIONS方法发起一个预检请求，第二次才是真正的异步请求，第一次的预检请求获知服务器是否
		允许该跨域请求：如果允许，才发起第二次真实的请求；如果不允许，则拦截第二次请求。*/
		response.setHeader("Access-Control-Max-Age", "0");//每次异步请求都发起预检请求，也就是说，发送两次请求。
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With,"
				+ " If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires,"
				+ " Content-Type, X-E4M-With,userId,token,access_token");//跨域请求允许包含的头
		response.setHeader("Access-Control-Allow-Credentials", "true");	//是否支持跨域，是否允许请求带有验证信息
		response.setHeader("XDomainRequestAllowed", "1");
    }


}  