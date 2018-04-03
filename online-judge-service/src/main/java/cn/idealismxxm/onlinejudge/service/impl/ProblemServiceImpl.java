package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.DescriptionDao;
import cn.idealismxxm.onlinejudge.dao.ProblemDao;
import cn.idealismxxm.onlinejudge.dao.TestCaseDao;
import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    private TestCaseDao testCaseDao;

    @Override
    public Problem getProblemById(Integer problemId) {
        // 参数校验
        if (problemId == null || problemId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 读库
        Problem problem;
        try {
            problem = problemDao.selectProblemById(problemId);
        } catch (Exception e) {
            LOGGER.error("#getProblemById error, problemId: {}", problemId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }

        // 验证数据是否存在
        if (problem == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.PROBLEM_NOT_EXIST);
        }
        return problem;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Integer addProblem(Problem problem, Description description, List<TestCase> testCases) {
        // 1. 初始化 problem 和 description 实例
        this.initProblemAndDescription(problem, description);

        // 本平台题目必须有测试用例
        if (OnlineJudgeEnum.THIS.getCode().equals(problem.getOriginalOj()) && CollectionUtils.isEmpty(testCases)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 数据入库
        try {
            descriptionDao.insertDescription(description);

            problem.setDescriptionId(description.getId());
            problemDao.insertProblem(problem);
            // 本平台题目题号与主键相同，且有测试用例
            if (OnlineJudgeEnum.THIS.getCode().equals(problem.getOriginalOj())) {
                Problem newProblem = new Problem();
                newProblem.setId(problem.getId());
                newProblem.setOriginalId(newProblem.getId().toString());
                problemDao.updateNonEmptyProblemById(problem);

                // 测试用例设置题目id
                testCases.forEach(testCase -> testCase.setProblemId(problem.getId()));
                int insertedRow = testCaseDao.insertTestCaseByBatch(testCases);
                if (insertedRow != testCases.size()) {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.DATA_SAVE_ERROR);
                }
            }

            return problem.getId();
        } catch (Exception e) {
            LOGGER.error("#addProblem error, problem: {}, description: {}", JsonUtil.objectToJson(problem), JsonUtil.objectToJson(description), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Boolean editProblem(Problem problem, Description description, List<TestCase> testCases) {
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

            // 2. 题目和描述入库
            problemDao.updateNonEmptyProblemById(problem);
            descriptionDao.updateNonEmptyDescriptionById(description);

            // 3. 测试用例入库
            this.saveTestCases(oldProblem.getId(), oldProblem.getOriginalOj(), testCases);

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
     * @param problem     题目
     * @param description 题目描述
     */
    private void initProblemAndDescription(Problem problem, Description description) {
        // 1. 参数校验
        if (problem == null || OnlineJudgeEnum.getOnlineJudgeEnumByCode(problem.getOriginalOj()) == null) {
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

    /**
     * 保存本平台题目的测试用例
     *
     * @param problemId  题目id
     * @param originalOj 原始oj
     * @param testCases  测试用例列表
     */
    private void saveTestCases(Integer problemId, Integer originalOj, List<TestCase> testCases) {
        // 只有本平台的题目需要保存测试用例
        if (OnlineJudgeEnum.THIS.getCode().equals(originalOj)) {
            if (CollectionUtils.isEmpty(testCases)) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
            }
            // 将测试用例分为待保存的和待添加的
            List<TestCase> toBeInsertedTestCases = new ArrayList<>(testCases.size());
            List<TestCase> toBeUpdatedTestCases = new ArrayList<>(testCases.size());
            testCases.forEach(testCase -> {
                testCase.setProblemId(problemId);
                if (testCase.getId() == null) {
                    toBeInsertedTestCases.add(testCase);
                } else {
                    toBeUpdatedTestCases.add(testCase);
                }
            });

            // 数据入库
            if (CollectionUtils.isNotEmpty(toBeInsertedTestCases)) {
                int insertedRow = testCaseDao.insertTestCaseByBatch(toBeInsertedTestCases);
                if (insertedRow != toBeInsertedTestCases.size()) {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.DATA_SAVE_ERROR);
                }
            }
            if (CollectionUtils.isNotEmpty(toBeUpdatedTestCases)) {
                int updatedRow = 0;
                for (TestCase testCase : toBeUpdatedTestCases) {
                    updatedRow += testCaseDao.updateNonEmptyTestCaseById(testCase);
                }
                if (updatedRow != toBeUpdatedTestCases.size()) {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.DATA_SAVE_ERROR);
                }
            }
        }
    }
}