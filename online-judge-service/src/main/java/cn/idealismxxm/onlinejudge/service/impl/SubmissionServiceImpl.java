package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.SubmissionDao;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.ActiveMQQueueEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import cn.idealismxxm.onlinejudge.service.jms.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 提交记录相关操作接口实现
 *
 * @author idealism
 * @date 2018/3/30
 */
@Service("submissionService")
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    @Resource
    private SubmissionDao submissionDao;

    @Resource
    private ProblemService problemService;

    @Resource
    private MessageProducer messageProducer;

    @Override
    public Submission getSubmissionById(Integer submissionId) {
        // 参数校验
        if (submissionId == null || submissionId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 读库
        Submission submission;
        try {
            submission = submissionDao.selectSubmissionById(submissionId);
        } catch (Exception e) {
            LOGGER.error("#getSubmissionById error, submissionId: {}", submissionId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        // 验证数据是否存在
        if (submission == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.SUBMISSION_NOT_EXIST);
        }
        return submission;
    }

    @Override
    public Integer submit(Submission submission) {
        if (submission == null || submission.getProblemId() == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 获取题目信息
        Problem problem = problemService.getProblemById(submission.getProblemId());
        try {
            // 未判题时没有其他OJ提交帐号，默认设为 -1
            submission.setRemoteAccountId(-1);
            // 未判题时没有其他OJ提交记录，默认设为 -1
            submission.setRemoteSubmissionId(-1);
            // 未判题时没有执行时间，默认设为 -1
            submission.setTime(-1);
            // 未判题时没有使用空间，默认设为 -1
            submission.setMemory(-1);
            submission.setResult(ResultEnum.QUEUING.getCode());

            // 数据入库
            submissionDao.insertSubmission(submission);

            // 发送 mq 给判题端
            messageProducer.sendTextMessage(ActiveMQQueueEnum.SUBMISSION_SUBMIT_QUEUE.getName(), submission.getId().toString());

            return submission.getId();
        } catch (BusinessException e) {
            LOGGER.error("#submit error, submission: {}", JsonUtil.objectToJson(submission), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("#submit error, submission: {}", JsonUtil.objectToJson(submission), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }

    @Override
    public Boolean modifySubmission(Submission submission) {
        int updatedRow;
        try{
            updatedRow = submissionDao.updateNonEmptySubmissionById(submission);
        } catch (Exception e) {
            LOGGER.error("#modifySubmission error, submission: {}", JsonUtil.objectToJson(submission), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
        if (updatedRow != 1) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DATA_SAVE_ERROR);
        }
        return true;
    }

    @Override
    public Pagination<Submission> pageSubmissionByQueryParam(QueryParam queryParam) {
        // 1. 参数校验
        if(queryParam == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 设置查询条件的map
        Map<String, Object> queryMap = new HashMap<>(16);
        queryMap.putAll(queryParam.getParam());

        // 3. 获取数据总数，并设置相关的分页信息
        Pagination<Submission> submissionPagination = new Pagination<>();
        submissionPagination.setPageSize(queryParam.getPageSize());
        Integer totalCount = submissionDao.countSubmissionByQueryMap(queryMap);
        Integer totalPage = totalCount / submissionPagination.getPageSize();
        if(totalCount % submissionPagination.getPageSize() != 0) {
            totalPage = totalPage + 1;
        }
        submissionPagination.setTotalCount(totalCount);
        submissionPagination.setTotalPage(totalPage);

        // 4. 如果查询页号超过页数，则设置当前页为最大页
        if(queryParam.getPageNum() > submissionPagination.getTotalPage()) {
            queryParam.setPageNum(submissionPagination.getTotalPage());
        }
        submissionPagination.setPageNum(queryParam.getPageNum());

        queryMap.put("offset", queryParam.getOffset());
        queryMap.put("limit", queryParam.getLimit());

        // 5. 若存在数据，则获取本页数据
        if(totalCount != 0) {
            submissionPagination.setData(submissionDao.pageSubmissionByQueryMap(queryMap));
        }

        return submissionPagination;
    }
}