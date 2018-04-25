package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.ContestContestantDao;
import cn.idealismxxm.onlinejudge.domain.entity.ContestContestant;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.RegexEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.RequestUtil;
import cn.idealismxxm.onlinejudge.service.ContestContestantService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 比赛者记录相关操作接口实现
 *
 * @author idealism
 * @date 2018/4/25
 */
@Service("contestContestantService")
public class ContestContestantServiceImpl implements ContestContestantService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ContestContestantServiceImpl.class);

    @Resource
    private ContestContestantDao contestContestantDao;


    @Override
    public Integer countByContestIdAndUsername(Integer contestId, String username) {
        // 1. 参数校验
        if(contestId == null || contestId <= 0 || StringUtils.isBlank(username)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 获取参赛信息个数
        try {
            return contestContestantDao.countByContestIdAndUsername(contestId, username);
        } catch (Exception e) {
            LOGGER.error("#countByContestIdAndUsername error, contestId: {}, username: {}", contestId, username, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public List<String> listContestContestantUsernameByContestId(Integer contestId) {
        // 1. 参数校验
        if(contestId == null || contestId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 获取参赛人列表
        try {
            return contestContestantDao.listContestContestantUsernameByContestId(contestId);
        } catch (Exception e) {
            LOGGER.error("#listContestContestantUsernameByContestId error, contestId: {}", contestId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public Integer register(Integer contestId, String username) {
        // 1. 参数校验
        if(contestId == null || contestId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        RegexEnum.USERNAME.validate(username);

        // 2. 防止插入重复数据
        if(this.countByContestIdAndUsername(contestId, username) != 0) {
            throw BusinessException.buildCustomizedMessageException("已报名成功，无需再次报名！");
        }

        // 3. 组装实例
        ContestContestant contestContestant = new ContestContestant();
        contestContestant.setContestId(contestId);
        contestContestant.setUsername(username);

        // 4. 数据入库
        try {
            contestContestantDao.insertContestContestant(contestContestant);
            return contestContestant.getId();
        } catch (Exception e) {
            LOGGER.error("#register error, e");
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }
}