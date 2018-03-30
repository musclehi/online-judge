package cn.idealismxxm.onlinejudge.service;
import java.util.List;
import cn.idealismxxm.onlinejudge.domain.entity.Description;
public interface DescriptionService{
	/**
	 * 获得Description数据的总行数
	 * @return
	 */
    long getDescriptionRowCount();
	/**
	 * 获得Description数据集合
	 * @return
	 */
    List<Description> selectDescription();
	/**
	 * 获得一个Description对象,以参数Description对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    Description selectDescriptionByObj(Description obj);
	/**
	 * 通过Description的id获得Description对象
	 * @param id
	 * @return
	 */
    Description selectDescriptionById(Integer id);
	/**
	 * 插入Description到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertDescription(Description value);
	/**
	 * 插入Description中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyDescription(Description value);
	/**
	 * 批量插入Description到数据库
	 * @param value
	 * @return
	 */
    int insertDescriptionByBatch(List<Description> value);
	/**
	 * 通过Description的id删除Description
	 * @param id
	 * @return
	 */
    int deleteDescriptionById(Integer id);
	/**
	 * 通过Description的id更新Description中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateDescriptionById(Description enti);
	/**
	 * 通过Description的id更新Description中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyDescriptionById(Description enti);
}