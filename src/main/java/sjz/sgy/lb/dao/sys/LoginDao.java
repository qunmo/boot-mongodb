package sjz.sgy.lb.dao.sys;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
* @author 郄俊杰
* @version 创建时间：2020年2月5日 下午3:24:02
* 类说明
*/
@Mapper
public interface LoginDao {

	Map<String, Object> getSysUser(Map<String, Object> map);
	Map<String, Object> getMember(Map<String, Object> map);
}
