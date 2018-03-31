package cn.idealismxxm.onlinejudge.dao;

import cn.idealismxxm.onlinejudge.domain.entity.TestCase;

import java.util.List;
public interface TestCaseDao{
	/**
	 * 获得TestCase数据的总行数
	 * @return
	 */
    long getTestCaseRowCount();
	/**
	 * 获得TestCase数据集合
	 * @return
	 */
    List<TestCase> selectTestCase();
	/**
	 * 获得一个TestCase对象,以参数TestCase对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    TestCase selectTestCaseByObj(TestCase obj);
	/**
	 * 通过TestCase的id获得TestCase对象
	 * @param id
	 * @return
	 */
    TestCase selectTestCaseById(Integer id);
	/**
	 * 插入TestCase到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertTestCase(TestCase value);
	/**
	 * 插入TestCase中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyTestCase(TestCase value);
	/**
	 * 批量插入TestCase到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertTestCaseByBatch(List<TestCase> value);
	/**
	 * 通过TestCase的id删除TestCase
	 * @param id
	 * @return
	 */
    int deleteTestCaseById(Integer id);
	/**
	 * 通过TestCase的id更新TestCase中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateTestCaseById(TestCase enti);
	/**
	 * 通过TestCase的id更新TestCase中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyTestCaseById(TestCase enti);

	/**
	 * 返回 problemId 下的所有测试用例
	 *
	 * @param problemId 题目id
	 * @return 测试用例列表
	 */
	List<TestCase> listTestCaseByProblemId(Integer problemId);
}