package sjz.sgy.lb.service.tbStatic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjz.sgy.lb.dao.tbStatic.TbStaticDao;
import sjz.sgy.lb.entity.tbStatic.TbStaticCustom;
import sjz.sgy.lb.entity.tbStatic.TbStaticQueryVo;
import sjz.sgy.lb.exception.RRException;

/**
 * @description 字典管理业务逻辑层
 * @author 申光跃
 *
 */
@Service
public class TbStaticService {
	
	@Autowired
	private TbStaticDao tbStaticDao;
	
	/**
	 * @description 新增字典
	 * @author 申光跃
	 * @param tbStaticCustom
	 */
	public void addTbStatic(TbStaticCustom tbStaticCustom){
		try {
			tbStaticDao.addTbStatic(tbStaticCustom);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("新增字典失败");
		}
	}
	
	/**
	 * @description 查询字典
	 * @author 申光跃
	 * @param tbStaticQueryVo
	 * @return
	 */
	public List<TbStaticCustom> queryTbStatic(TbStaticQueryVo tbStaticQueryVo){
		List<TbStaticCustom> tbStaticArray;
		try {
			tbStaticArray = tbStaticDao.queryTbStatic(tbStaticQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("字典查询失败");
		}
		return tbStaticArray;
	}
	
	/**
	 * @description 查询单条字典记录
	 * @author 申光跃
	 * @param tbStaticQueryVo
	 * @return
	 */
	public TbStaticCustom queryOneTbStatic(TbStaticQueryVo tbStaticQueryVo){
		TbStaticCustom tbStaticCustom = null;
		List<TbStaticCustom> tbStaticArray;
		try {
			tbStaticArray = tbStaticDao.queryTbStatic(tbStaticQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("字典查询失败");
		}
		if (tbStaticArray!=null&&tbStaticArray.size()>0) {
			tbStaticCustom = tbStaticArray.get(0);
		}else {
			throw new RRException("查询字典记录不存在");
		}
		return tbStaticCustom;
	}
	
	
	
	/**
	 * @description 修改字典
	 * @author 申光跃
	 * @param tbStaticCustom
	 */
	public void editTbStatic(TbStaticCustom tbStaticCustom) {
		try {
			int a = 1/0;
			tbStaticDao.editTbStatic(tbStaticCustom);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("字典修改失败");
		}
	}
	
}
