package cn.idealismxxm.onlinejudge.service;

import java.util.List;

/**
 * 比赛者记录相关操作接口
 *
 * @author idealism
 * @date 2018/4/25
 */
public interface ContestContestantService {

    /**
     * 统计contestId下所有username的参赛信息个数
     *
     * @param contestId 比赛id
     * @param username 用户名
     * @return 参赛信息个数
     */
    Integer countByContestIdAndUsername(Integer contestId, String username);

    /**
     * 获取contestId下所有参赛人用户名列表
     *
     * @param contestId 比赛id
     * @return 参赛人用户名列表
     */
    List<String> listContestContestantUsernameByContestId(Integer contestId);

    /**
     * 用户 username 注册 比赛 contestId
     *
     * @param contestId 比赛id
     * @param username  用户名
     * @return 比赛人记录的id
     */
    Integer register(Integer contestId, String username);
}