package sjz.sgy.lb.dao.tbStatic;

import java.util.List;

import sjz.sgy.lb.entity.tbStatic.TbStaticCustom;
import sjz.sgy.lb.entity.tbStatic.TbStaticQueryVo;

/**
 * @description 字典管理持久层
 * @author 申光跃
 *
 */
public interface TbStaticDao {
	
	/**
	 * @description 新增字典
	 * @author 申光跃
	 * @param tbStaticCustom
	 * @return
	 */
	Integer addTbStatic(TbStaticCustom tbStaticCustom) throws Exception;
	
	/**
	 * @description 查询字典
	 * @author 申光跃
	 * @param tbStaticQueryVo
	 * @return
	 */
	List<TbStaticCustom> queryTbStatic(TbStaticQueryVo tbStaticQueryVo) throws Exception;
	
	/**
	 * @description 修改字典
	 * @author 申光跃
	 * @param tbStaticCustom
	 * @return
	 */
	Integer editTbStatic(TbStaticCustom tbStaticCustom) throws Exception;
	
}
