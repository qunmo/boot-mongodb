package sjz.sgy.lb.service.member;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjz.sgy.lb.dao.member.SysUserMapper;
import sjz.sgy.lb.entity.member.SysUser;
import sjz.sgy.lb.entity.member.SysUserExample;
import sjz.sgy.lb.entity.member.SysUserExample.Criteria;
import sjz.sgy.lb.entity.member.SysUserParam;
import sjz.sgy.lb.exception.RRException;
import sjz.sgy.lb.util.ConsoleConstant;

@Service
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	/**
	 * 
	 * @Title: addSysUserInf
	 * @Description: TODO：新增用户信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:42:41
	 */
	public void addSysUserInf(SysUser sysUser) throws Exception {
		if (StringUtils.isEmpty(sysUser.getUsername())) {
			throw new RRException("用户名不能为空");
		}
		sysUser.setCreateTime(new Date());
		sysUser.setModifyTime(new Date());
		sysUser.setDelState(ConsoleConstant.DEL_STATE_NO);
		sysUserMapper.insert(sysUser);
	}

	/**
	 * 
	 * @Title: updateSysUserInf
	 * @Description: TODO：更新用户信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:45:48
	 */
	public void updateSysUserInf(SysUser sysUser) throws Exception {

		sysUserMapper.updateByPrimaryKeySelective(sysUser);
	}

	/**
	 * 
	 * @Title: delMemberById
	 * @Description: TODO：根据用户id删除用户
	 * @author gyk
	 * @date 2020年2月6日 下午2:58:34
	 */
	public void delSysUserById(SysUser sysUser) throws Exception {

		sysUserMapper.updateByPrimaryKeySelective(sysUser);
	}

	/**
	 * 
	 * @Title: queryMemberInfList
	 * @Description: TODO：模糊查询用户列表
	 * @author gyk
	 * @date 2020年2月6日 下午3:56:51
	 */
	public List<SysUser> querySysUserInfList(SysUserParam sysUser) throws Exception {
		// PageHelper.startPage(page, rows);
		SysUserExample example = new SysUserExample();
		
		Criteria sysUserCriteria = example.createCriteria();
		if (sysUser.getUsername() != null && !sysUser.getUsername().equals("")) {
			sysUserCriteria.andUsernameLike("%" + sysUser.getUsername() + "%");
		}
		if (sysUser.getMobile() != null && !sysUser.getMobile().equals("")) {
			sysUserCriteria.andMobileLike("%" + sysUser.getMobile() + "%");
		}
		
		if (sysUser.getIdentity() != null && sysUser.getIdentity() != 0) {
			sysUserCriteria.andIdentityEqualTo(sysUser.getIdentity());
		}
		if (sysUser.getSysUserId() != null && sysUser.getSysUserId() != 0) {
			sysUserCriteria.andSysUserIdEqualTo(sysUser.getSysUserId());
		}
		sysUserCriteria.andDelStateEqualTo(ConsoleConstant.DEL_STATE_NO);

		return sysUserMapper.selectByExample(example);

	}
	
	/**
	 * 
	 * @Title: querySysUserInfById
	 * @Description: TODO：根据用户id查询用户信息（回显）
	 * @author gyk
	 * @date 2020年2月6日 下午5:19:55
	 */
	public SysUser querySysUserInfById(int sysUserId) throws Exception{
		
		return sysUserMapper.selectByPrimaryKey(sysUserId);
		
	}

}
