package cn.idealismxxm.onlinejudge.dao;

import cn.idealismxxm.onlinejudge.domain.entity.Problem;

import java.util.List;

public interface ProblemDao {
    /**
     * 获得Problem数据的总行数
     *
     * @return
     */
    long getProblemRowCount();

    /**
     * 获得Problem数据集合
     *
     * @return
     */
    List<Problem> selectProblem();

    /**
     * 获得一个Problem对象,以参数Problem对象中不为空的属性作为条件进行查询
     *
     * @param obj
     * @return
     */
    Problem selectProblemByObj(Problem obj);

    /**
     * 通过Problem的id获得Problem对象
     *
     * @param id
     * @return
     */
    Problem selectProblemById(Integer id);

    /**
     * 插入Problem到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertProblem(Problem value);

    /**
     * 插入Problem中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    int insertNonEmptyProblem(Problem value);

    /**
     * 批量插入Problem到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertProblemByBatch(List<Problem> value);

    /**
     * 通过Problem的id删除Problem
     *
     * @param id
     * @return
     */
    int deleteProblemById(Integer id);

    /**
     * 通过Problem的id更新Problem中的数据,包括null值
     *
     * @param enti
     * @return
     */
    int updateProblemById(Problem enti);

    /**
     * 通过Problem的id更新Problem中属性不为null的数据
     *
     * @param enti
     * @return
     */
    int updateNonEmptyProblemById(Problem enti);
}