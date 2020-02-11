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

import sjz.sgy.lb.entity.member.Member;
import sjz.sgy.lb.entity.member.MemberParam;
import sjz.sgy.lb.service.member.MemberService;
import sjz.sgy.lb.util.R;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	/**
	 * 
	 * @Title: addMemberInf
	 * @Description: TODO：新增会员信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:42:41
	 */
	@PostMapping("/member/addMemberInf")
	public R addMemberInf(@RequestBody Member member) {

		try {
			memberService.addMemberInf(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	/**
	 * 
	 * @Title: updateMemberInf
	 * @Description: TODO：更新会员信息
	 * @author gyk
	 * @date 2020年2月6日 下午2:45:48
	 */
	@PostMapping("/member/updateMemberInf")
	public R updateMemberInf(@RequestBody Member member) {

		try {
			memberService.updateMemberInf(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	/**
	 * 
	 * @Title: delMemberById
	 * @Description: TODO：根据会员id删除会员
	 * @author gyk
	 * @date 2020年2月6日 下午2:58:34
	 */
	@PostMapping("/member/delMemberById")
	public R delMemberById(@RequestBody Member member) {

		try {
			memberService.delMemberById(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		return R.ok();

	}

	/**
	 * 
	 * @Title: queryMemberInfList
	 * @Description: TODO：模糊查询会员列表
	 * @author gyk
	 * @date 2020年2月6日 下午3:56:51
	 */
	@PostMapping("/member/queryMemberInfList")
	public R queryMemberInfList(@RequestBody MemberParam member) {

		int pageNum = member.getPageNum() == 0 ? 1 : member.getPageNum();
		int pageSize = member.getPageSize() == 0 ? 10 : member.getPageSize();
		PageHelper.startPage(pageNum, pageSize);

		List<Member> memberList = null;
		try {
			memberList = memberService.queryMemberInfList(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}
		PageInfo<Member> pageInfo = new PageInfo<>(memberList);

		return R.ok().put("memberListInf", memberList).put("count", pageInfo.getTotal());

	}

	/**
	 * 
	 * @Title: queryMemberInfById
	 * @Description: TODO：根据会员id查询会员用户信息
	 * @author gyk
	 * @date 2020年2月7日 上午9:59:26
	 */
	@GetMapping("/member/queryMemberInfById")
	public R queryMemberInfById(@RequestParam int memberId) {

		Member memberInf = null;
		try {
			memberInf = memberService.queryMemberInfById(memberId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return R.error(e.getMessage());
		}

		return R.ok().put("memberInf", memberInf);
	}

}
