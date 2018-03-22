package cn.idealismxxm.onlinejudge.service;

import java.util.List;

import cn.idealismxxm.onlinejudge.entity.OriginalProblem;

public interface OriginalProblemService {
    /**
     * 获得OriginalProblem数据的总行数
     *
     * @return
     */
    long getOriginalProblemRowCount();

    /**
     * 获得OriginalProblem数据集合
     *
     * @return
     */
    List<OriginalProblem> selectOriginalProblem();

    /**
     * 获得一个OriginalProblem对象,以参数OriginalProblem对象中不为空的属性作为条件进行查询
     *
     * @param obj
     * @return
     */
    OriginalProblem selectOriginalProblemByObj(OriginalProblem obj);

    /**
     * 通过OriginalProblem的id获得OriginalProblem对象
     *
     * @param id
     * @return
     */
    OriginalProblem selectOriginalProblemById(Integer id);

    /**
     * 插入OriginalProblem到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertOriginalProblem(OriginalProblem value);

    /**
     * 插入OriginalProblem中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    int insertNonEmptyOriginalProblem(OriginalProblem value);

    /**
     * 批量插入OriginalProblem到数据库
     *
     * @param value
     * @return
     */
    int insertOriginalProblemByBatch(List<OriginalProblem> value);

    /**
     * 通过OriginalProblem的id删除OriginalProblem
     *
     * @param id
     * @return
     */
    int deleteOriginalProblemById(Integer id);

    /**
     * 通过OriginalProblem的id更新OriginalProblem中的数据,包括null值
     *
     * @param enti
     * @return
     */
    int updateOriginalProblemById(OriginalProblem enti);

    /**
     * 通过OriginalProblem的id更新OriginalProblem中属性不为null的数据
     *
     * @param enti
     * @return
     */
    int updateNonEmptyOriginalProblemById(OriginalProblem enti);
}