package sjz.sgy.lb.controller.tbStatic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import sjz.sgy.lb.entity.tbStatic.TbStaticCustom;
import sjz.sgy.lb.entity.tbStatic.TbStaticQueryVo;
import sjz.sgy.lb.service.tbStatic.TbStaticService;
import sjz.sgy.lb.util.R;

/**
 * @description 字典管理控制层
 * @author 申光跃
 *
 */
@Controller
public class TbStaticController {
	
	@Autowired
	private TbStaticService tbStaticService;
	
	/**
	 * @description 新增字典
	 * @author 申光跃
	 * @param tbStaticCustom
	 * @return
	 */
	@PostMapping("/tbStatic/addTbStatic")
	@ResponseBody
	public R addTbStatic(@RequestBody TbStaticCustom tbStaticCustom) {
		try {
			tbStaticService.addTbStatic(tbStaticCustom);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
	/**
	 * @description 分页查询字典
	 * @author 申光跃
	 * @param tbStaticQueryVo
	 * @return
	 */
	@PostMapping("/tbStatic/queryTbStatic")
	@ResponseBody
	public R queryTbStatic(@RequestBody TbStaticQueryVo tbStaticQueryVo) {
		PageHelper.startPage(tbStaticQueryVo.getPage(), tbStaticQueryVo.getLimit());
		List<TbStaticCustom> tbStaticArray;
		try {
			tbStaticArray = tbStaticService.queryTbStatic(tbStaticQueryVo);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}		
		PageInfo<TbStaticCustom> tbStaticPageInfo = new PageInfo<>(tbStaticArray);		
		return R.ok().put("tbStaticArray", tbStaticPageInfo.getList()).put("count", tbStaticPageInfo.getTotal());
	}
	
	/**
	 * @description 查询单个字典
	 * @author 申光跃
	 * @param tbStaticQueryVo
	 * @return
	 */
	@PostMapping("/tbStatic/queryOneTbStatic")
	@ResponseBody
	public R queryOneTbStatic(@RequestBody TbStaticQueryVo tbStaticQueryVo) {
		
		TbStaticCustom tbStatic;
		try {
			tbStatic = tbStaticService.queryOneTbStatic(tbStaticQueryVo);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}				
		return R.ok().put("tbStatic", tbStatic);
	}
	
	
	
	/**
	 * @description 修改字典数据
	 * @author 申光跃
	 * @param tbStaticCustom
	 * @return
	 */
	@PostMapping("/tbStatic/editTbStatic")
	@ResponseBody
	public R editTbStatic(@RequestBody TbStaticCustom tbStaticCustom) {
		try {
			tbStaticService.editTbStatic(tbStaticCustom);
		} catch (Exception e) {
			return R.error("controller出错了");
		}
		return R.ok();
	}
	
}
