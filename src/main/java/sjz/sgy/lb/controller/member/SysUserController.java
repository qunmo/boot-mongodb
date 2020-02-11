package sjz.sgy.lb.controller.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import sjz.sgy.lb.entity.member.SysUser;
import sjz.sgy.lb.entity.member.SysUserParam;
import sjz.sgy.lb.service.member.SysUserService;
import sjz.sgy.lb.util.R;

@RestController
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 
	 * @Title: addSysUserInf
	 * @Description: TODO：新增用户信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:42:41
	 */
	@PostMapping("/sysUser/addSysUserInf")
	public R addSysUserInf(@RequestBody SysUser sysUser) {

		try {
			sysUserService.addSysUserInf(sysUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	/**
	 * 
	 * @Title: updateSysUserInf
	 * @Description: TODO：更新用户信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:45:48
	 */
	@PostMapping("/sysUser/updateSysUserInf")
	public R updateSysUserInf(@RequestBody SysUser sysUser) {

		try {
			sysUserService.updateSysUserInf(sysUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	/**
	 * 
	 * @Title: delSysUserById
	 * @Description: TODO：根据用户id删除用户
	 * @author gyk
	 * @date 2020年2月6日 下午2:58:34
	 */
	@PostMapping("/sysUser/delSysUserById")
	public R delSysUserById(@RequestBody SysUser sysUser) {

		try {
			sysUserService.delSysUserById(sysUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		return R.ok();

	}

	/**
	 * 
	 * @Title: querySysUserInfList
	 * @Description: TODO：模糊查询用户列表
	 * @author gyk
	 * @date 2020年2月6日 下午3:56:51
	 */
	@PostMapping("/sysUser/querySysUserInfList")
	public R querySysUserInfList(@RequestBody SysUserParam sysUser) {
		int pageNum = sysUser.getPageNum() == 0 ? 1 : sysUser.getPageNum();
		int pageSize = sysUser.getPageSize() == 0 ? 10 : sysUser.getPageSize();
		PageHelper.startPage(pageNum, pageSize);
		
		List<SysUser> sysUserInfList = null;
		try {
			sysUserInfList = sysUserService.querySysUserInfList(sysUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserInfList);
		return R.ok().put("sysUserInfList", sysUserInfList).put("count", pageInfo.getTotal());

	}

	/**
	 * 
	 * @Title: querySysUserInfById
	 * @Description: TODO：根据用户id查询用户信息（回显）
	 * @author gyk
	 * @date 2020年2月6日 下午5:19:55
	 */
	@GetMapping("/sysUser/querySysUserInfById")
	public R querySysUserInfById(@RequestParam int sysUserId) {

		SysUser sysUser = null;

		try {
			sysUser = sysUserService.querySysUserInfById(sysUserId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}

		return R.ok().put("sysUserInf", sysUser);

	}

}
