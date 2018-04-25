package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Contest;

/**
 * 比赛相关操作接口
 *
 * @author idealism
 * @date 2018/4/24
 */
public interface ContestService {

    /**
     * 根据id获取比赛实例
     *
     * @param id 主键
     * @return 比赛实例
     */
    Contest getContestById(Integer id);

    /**
     * 添加比赛
     *
     * @param contest 比赛实例
     * @return 比赛的id
     */
    Integer addContest(Contest contest);

    /**
     * 编辑比赛
     *
     * @param contest 比赛实例
     * @return true / false
     */
    Boolean editContest(Contest contest);
}