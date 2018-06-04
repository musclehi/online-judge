package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.Tag;
import java.util.List;
import java.util.Map;

public interface TagDao{
	/**
	 * 获得Tag数据的总行数
	 * @return
	 */
    long getTagRowCount();
	/**
	 * 获得Tag数据集合
	 * @return
	 */
    List<Tag> selectTag();
	/**
	 * 获得一个Tag对象,以参数Tag对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    Tag selectTagByObj(Tag obj);
	/**
	 * 通过Tag的id获得Tag对象
	 * @param id
	 * @return
	 */
    Tag selectTagById(Integer id);
	/**
	 * 插入Tag到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertTag(Tag value);
	/**
	 * 插入Tag中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyTag(Tag value);
	/**
	 * 批量插入Tag到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertTagByBatch(List<Tag> value);
	/**
	 * 通过Tag的id删除Tag
	 * @param id
	 * @return
	 */
    int deleteTagById(Integer id);
	/**
	 * 通过Tag的id更新Tag中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateTagById(Tag enti);
	/**
	 * 通过Tag的id更新Tag中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyTagById(Tag enti);

	/**
	 * 统计 符合查询条件 的标签总数
	 *
	 * @param queryMap 查询条件的 map
	 * @return 标签总数
	 */
	int countTagByQueryMap(Map<String, Object> queryMap);

	/**
	 * 分页查询 符合查询条件 的标签
	 *
	 * @param queryMap 查询条件的 map
	 * @return 标签列表
	 */
	List<Tag> pageTagByQueryMap(Map<String, Object> queryMap);

    /**
     * 获取 主键 在 ids中的 标签列表
     *
     * @param ids id列表
     * @return 标签列表
     */
    List<Tag> listTagByIds(List<Integer> ids);
}