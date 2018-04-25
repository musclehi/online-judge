package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.ContestDao;
import cn.idealismxxm.onlinejudge.domain.entity.Contest;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 比赛相关操作接口实现
 *
 * @author idealism
 * @date 2018/4/24
 */
@Service("contestService")
public class ContestServiceImpl implements ContestService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ContestServiceImpl.class);

    @Resource
    private ContestDao contestDao;


    @Override
    public Integer addContest(Contest contest) {
        if(contest == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        try {
            contestDao.insertContest(contest);
            return contest.getId();
        } catch (Exception e) {
            LOGGER.error("#addContest error, contest: {}", JsonUtil.objectToJson(contest), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }
}