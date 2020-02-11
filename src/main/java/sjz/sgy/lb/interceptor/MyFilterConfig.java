package sjz.sgy.lb.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MyFilterConfig implements WebMvcConfigurer {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 登录拦截的管理器
		InterceptorRegistration registration = registry.addInterceptor(new SysInterceptor());
		// 拦截的对象会进入这个类中进行判断
		registration.addPathPatterns("/**");
		// 所有路径都被拦截
		registration.excludePathPatterns("/", "/login/consoleLogin", "/error", "/static/**", "/login/frontLogin",
				"/login/changeToken", "/login/captcha.jpg", "/pic/**");
		// 添加不拦截路径
		// super.addInterceptors(registry);
	}

}
