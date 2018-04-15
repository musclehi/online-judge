package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.TestCaseDao;
import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.TestCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试用例相关操作接口实现
 *
 * @author idealism
 * @date 2018/3/31
 */
@Service("testCaseService")
public class TestCaseServiceImpl implements TestCaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseServiceImpl.class);

    @Resource
    private TestCaseDao testCaseDao;

    @Override
    public List<TestCase> listTestCaseByProblemId(Integer problemId) {
        // 参数校验
        if (problemId == null || problemId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 读库
        List<TestCase> testCases;
        try {
            testCases = testCaseDao.listTestCaseByProblemId(problemId);
        } catch (Exception e) {
            LOGGER.error("#listTestCaseByProblemId error, problemId: {}", problemId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        // 验证数据是否存在
        if (CollectionUtils.isEmpty(testCases)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.TEST_CASE_NOT_EXIST);
        }
        return testCases;
    }
}