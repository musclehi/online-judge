package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.DescriptionDao;
import cn.idealismxxm.onlinejudge.dao.ProblemDao;
import cn.idealismxxm.onlinejudge.entity.Description;
import cn.idealismxxm.onlinejudge.entity.Problem;
import cn.idealismxxm.onlinejudge.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public Integer addProblem(String problemJson, String descriptionJson) {
        // 1. 获取 problem 和 description 实例
        Problem problem = new Problem();
        Description description = new Description();
        this.initProblemAndDescription(problemJson, descriptionJson, problem, description);

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
            LOGGER.error("#addProblem error, problemJson: {}, descriptionJson: {}", problemJson, descriptionJson, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Boolean editProblem(String problemJson, String descriptionJson) {
        if (StringUtils.isBlank(problemJson)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        try {
            // 1. 获取 problem 和 description 实例
            Problem problem = new Problem();
            // 验证对应关系是否正确
            Problem newProblem = JsonUtil.jsonToObject(problemJson, Problem.class);
            Problem oldProblem = this.getProblemById(newProblem.getId());

            Description description = new Description();
            this.initProblemAndDescription(problemJson, descriptionJson, problem, description);
            // 部分信息不修改
            problem.setDescriptionId(null);
            problem.setOriginalOj(null);
            problem.setOriginalId(null);
            problem.setUrl(null);
            description.setId(oldProblem.getDescriptionId());

            // 2. 数据入库
            problemDao.updateNonEmptyProblemById(problem);
            descriptionDao.updateNonEmptyDescriptionById(description);
            return true;
        } catch (BusinessException e) {
            LOGGER.error("#editProblem error, problemJson: {}, descriptionJson: {}", problemJson, descriptionJson, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("#editProblem error, problemJson: {}, descriptionJson: {}", problemJson, descriptionJson, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }

    /**
     * 初始化 problem 和 description 实例
     *
     * @param problemJson     题目json
     * @param descriptionJson 题目描述json
     * @param problem         题目json
     * @param description     题目描述json
     */
    private void initProblemAndDescription(String problemJson, String descriptionJson, Problem problem, Description description) {
        // 1. 参数校验
        if (StringUtils.isBlank(problemJson)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        Problem newProblem = JsonUtil.jsonToObject(problemJson, Problem.class);
        Integer originalOj = newProblem.getOriginalOj();
        String originalId = newProblem.getOriginalId();
        if (OnlineJudgeEnum.getOnlineJudgeEnumByCode(originalOj) == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        // 2. 本平台题目
        if (OnlineJudgeEnum.THIS.getCode().equals(originalOj)) {
            if (StringUtils.isBlank(descriptionJson)) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
            }
            BeanUtils.copyProperties(newProblem, problem);
            BeanUtils.copyProperties(JsonUtil.jsonToObject(descriptionJson, Description.class), description);

            return;
        }
        // 3. 其他平台题目
        if (StringUtils.isBlank(originalId)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        // TODO remote模块进行相关信息获取
    }
}