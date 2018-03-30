package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.DescriptionDao;
import cn.idealismxxm.onlinejudge.dao.ProblemDao;
import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 题目相关操作接口实现
 *
 * @author idealism
 * @date 2018/3/29
 */
@Service("problemService")
public class ProblemServiceImpl implements ProblemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemServiceImpl.class);

    @Resource
    private ProblemDao problemDao;

    @Resource
    private DescriptionDao descriptionDao;


    @Override
    public Problem getProblemById(Integer problemId) {
        // 参数校验
        if(problemId == null || problemId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 读库
        Problem problem;
        try {
             problem =  problemDao.selectProblemById(problemId);
        } catch (Exception e) {
            LOGGER.error("#getProblemById error, problemId: {}", problemId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }

        // 验证数据是否存在
        if(problem == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.PROBLEM_NOT_EXISTS);
        }
        return problem;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Integer addProblem(Problem problem, Description description) {
        // 1. 初始化 problem 和 description 实例
        this.initProblemAndDescription(problem, description);

        // 2. 数据入库
        try {
            descriptionDao.insertDescription(description);

            problem.setDescriptionId(description.getId());
            problemDao.insertProblem(problem);
            // 本平台题目题号与主键相同
            if(OnlineJudgeEnum.THIS.getCode().equals(problem.getOriginalOj())) {
                Problem newProblem = new Problem();
                newProblem.setId(problem.getId());
                newProblem.setOriginalId(newProblem.getId().toString());
                problemDao.updateNonEmptyProblemById(problem);
            }

            return problem.getId();
        } catch (Exception e) {
            LOGGER.error("#addProblem error, problem: {}, description: {}", JsonUtil.objectToJson(problem), JsonUtil.objectToJson(description), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Boolean editProblem(Problem problem, Description description) {
        try {
            // 1. 获取 problem 和 description 实例
            Problem oldProblem = this.getProblemById(problem.getId());
            problem.setOriginalOj(oldProblem.getOriginalOj());
            problem.setOriginalId(oldProblem.getOriginalId());

            this.initProblemAndDescription(problem, description);

            // 更新 url
            problem.setUrl(Objects.requireNonNull(OnlineJudgeEnum.getOnlineJudgeEnumByCode(problem.getOriginalOj())).getUrl(problem.getOriginalId()));

            // 部分信息不修改
            problem.setDescriptionId(null);
            problem.setOriginalOj(null);
            problem.setOriginalId(null);

            description.setId(oldProblem.getDescriptionId());

            // 2. 数据入库
            problemDao.updateNonEmptyProblemById(problem);
            descriptionDao.updateNonEmptyDescriptionById(description);
            return true;
        } catch (BusinessException e) {
            LOGGER.error("#editProblem error, problem: {}, description: {}", JsonUtil.objectToJson(problem), JsonUtil.objectToJson(description), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("#editProblem error, problem: {}, description: {}", JsonUtil.objectToJson(problem), JsonUtil.objectToJson(description), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }

    /**
     * 初始化 problem 和 description 实例
     *
     * @param problem         题目
     * @param description     题目描述
     */
    private void initProblemAndDescription(Problem problem, Description description) {
        // 1. 参数校验
        if(problem == null || OnlineJudgeEnum.getOnlineJudgeEnumByCode(problem.getOriginalOj()) == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        Integer originalOj = problem.getOriginalOj();
        String originalId = problem.getOriginalId();

        // 2. 本平台题目
        if (OnlineJudgeEnum.THIS.getCode().equals(originalOj)) {
            if (description == null) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
            }
            return;
        }

        // 3. 其他平台题目
        if (StringUtils.isBlank(originalId)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        // TODO remote模块进行相关信息获取
    }
}