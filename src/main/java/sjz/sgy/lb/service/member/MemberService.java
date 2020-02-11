package sjz.sgy.lb.service.member;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjz.sgy.lb.dao.member.MemberMapper;
import sjz.sgy.lb.entity.member.Member;
import sjz.sgy.lb.entity.member.MemberExample;
import sjz.sgy.lb.entity.member.MemberExample.Criteria;
import sjz.sgy.lb.entity.member.MemberParam;
import sjz.sgy.lb.exception.RRException;
import sjz.sgy.lb.util.ConsoleConstant;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;

	/**
	 * 
	 * @Title: addMemberInf
	 * @Description: TODO：新增用户信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:42:41
	 */
	public void addMemberInf(Member member) throws Exception {
		if (StringUtils.isEmpty(member.getAccountNumber())) {
			throw new RRException("用户名不能为空");
		}
		member.setCreateTime(new Date());
		member.setModifyTime(new Date());
		member.setDelState(ConsoleConstant.DEL_STATE_NO);
		memberMapper.insert(member);
	}

	/**
	 * 
	 * @Title: updateMemberInf
	 * @Description: TODO：更新会员信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:45:48
	 */
	public void updateMemberInf(Member member) throws Exception {

		memberMapper.updateByPrimaryKeySelective(member);
	}

	/**
	 * 
	 * @Title: delMemberById
	 * @Description: TODO：根据会员id删除会员
	 * @author gyk
	 * @date 2020年2月6日 下午2:58:34
	 */
	public void delMemberById(Member member) throws Exception {

		memberMapper.updateByPrimaryKeySelective(member);
	}

	/**
	 * 
	 * @Title: queryMemberInfList
	 * @Description: TODO：模糊查询会员列表
	 * @author gyk
	 * @date 2020年2月6日 下午3:56:51
	 */
	public List<Member> queryMemberInfList(MemberParam member) throws Exception {
		
		MemberExample example = new MemberExample();
		Criteria memberCriteria = example.createCriteria();
		if (member.getWechatNickname() != null && !member.getWechatNickname().equals("")) {
			memberCriteria.andWechatNicknameLike("%" + member.getWechatNickname() + "%");
		}
		if (member.getAccountNumber() != null && !member.getAccountNumber().equals("")) {
			memberCriteria.andAccountNumberLike("%" + member.getAccountNumber() + "%");
		}
		if (member.getPhone() != null && !member.getPhone().equals("")) {
			memberCriteria.andPhoneLike("%" + member.getPhone() + "%");
		}
		if (member.getMemberId() != null && member.getMemberId() != 0) {
			memberCriteria.andMemberIdEqualTo(member.getMemberId());
		}
		memberCriteria.andDelStateEqualTo(ConsoleConstant.DEL_STATE_NO);

		return memberMapper.selectByExample(example);

	}
	
	/**
	 * 
	 * @Title: queryMemberInfById
	 * @Description: TODO：根据会员id查询会员用户信息
	 * @author gyk
	 * @date 2020年2月7日 上午9:56:32
	 */
	public Member queryMemberInfById(int memberId) throws Exception{
		return memberMapper.selectByPrimaryKey(memberId);
	}

}
