package sjz.sgy.lb.config.druid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * @Description: TODO(这里用一句话描述这个方法的作用) 
 * @author 于浩淼
 * @date 2020年2月6日 上午10:25:33
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*", 
    initParams={
            //@WebInitParam(name="allow",value="192.168.1.20,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
            @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
            @WebInitParam(name="loginUsername",value="admin"),// 用户名
            @WebInitParam(name="loginPassword",value="admin123456"),// 密码
            @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
    })

public class DruidStatViewServlet extends StatViewServlet{

	
}
