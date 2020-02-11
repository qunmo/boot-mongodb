package sjz.sgy.lb.controller;

import org.springframework.beans.factory.annotation.Autowired;

import sjz.sgy.lb.service.sys.InterceptService;

/**
* @author 郄俊杰
* @version 创建时间：2020年2月7日 上午9:57:43
* 类说明
*/
public class BaseController {

	@Autowired
	private InterceptService interceptService;


	/**
	 * @Title: getUserId
	 * @Description: 获取userId 
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月7日 上午9:59:46
	 */
	public String getUserId() {
		return interceptService.getUserId();
	}
	/**
	 * @Title: getToken
	 * @Description: 获取token
	 * @author 郄俊杰
	 * @param:
	 * @return_type:
	 * @date 2020年2月7日 上午9:59:46
	 */
	public String getToken() {
		return interceptService.getToken();
	}
}
