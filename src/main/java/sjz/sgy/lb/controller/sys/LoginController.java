package sjz.sgy.lb.controller.sys;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import sjz.sgy.lb.controller.BaseController;
import sjz.sgy.lb.service.sys.LoginService;
import sjz.sgy.lb.util.R;




/**
* @author 郄俊杰
* @version 创建时间：2020年2月4日 下午4:43:52
* 类说明
*/
@Controller
public class LoginController extends BaseController{

	@Autowired
	private Producer producer;
	@Autowired
	private LoginService loginService;
	/**
	 * @Title: captcha
	 * @Description: 生成图片验证码
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月4日 下午4:44:39
	 */
	@RequestMapping("/login/captcha.jpg")
	public void captcha(HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到 session
		HttpSession session=request.getSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
		//ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * @Title: consoleLogin
	 * @Description: 后台管理用户登录
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月5日 下午3:07:44
	 */
	@PostMapping("/login/consoleLogin")
	@ResponseBody
	public R consoleLogin(@RequestBody Map<String, Object> sysUser,HttpServletRequest request) {
		//校验验证码
		if(!sysUser.containsKey("captcha")){
			return R.error("验证码不能为空");
		}
		if(null==sysUser.get("captcha")||("").equals(sysUser.get("captcha").toString())){
			return R.error("验证码不能为空");
		}
		HttpSession session=request.getSession();
		String text=session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
		if(!sysUser.get("captcha").toString().equals(text)){
			return R.error("验证码错误");
		}
		 //验证账号密码，返回token
		Map<String, Object> result;
		result = loginService.login(sysUser);
		
		return R.ok().put("data", result);
	}
	/**
	 * @Title: frontLogin
	 * @Description: 前端用户登录
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月5日 下午3:07:44
	 */
	@PostMapping("/login/frontLogin")
	@ResponseBody
	public R frontLogin(@RequestBody Map<String, Object> memberUser) {
		 //验证账号密码，返回token
		Map<String, Object> result;
		result = loginService.loginFront(memberUser);
		return R.ok().put("data", result);
	}
	/**
	 * @Title: changeToken
	 * @Description: 双token机制，长token换短token
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月7日 上午9:23:19
	 */
	@PostMapping("/login/changeToken")
	@ResponseBody
	public R changeToken(@RequestBody Map<String, Object> map){
		if(map.isEmpty()){
			return R.error("refreshToken为空");
		}
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("accessToken", loginService.changeToken(map));
		return R.ok().put("data", data);
	}
	/**
	 * @Title: logout
	 * @Description: 退出
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月7日 上午9:27:07
	 */
	@GetMapping("/login/logout")
	@ResponseBody
	public R logout(){
		loginService.logout();
		return R.ok("退出成功");
	}
	
	/**
	 * @Title: getString
	 * @Description: 测试
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月7日 上午9:23:53
	 */
	@GetMapping("/login")
	@ResponseBody
	public R getString(){
		
		return R.ok("成功了").put("userId", getUserId());
	}
}
