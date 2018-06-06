package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.SubmissionDao;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.ActiveMQQueueEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.enums.VisibleStatusEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;
import cn.idealismxxm.onlinejudge.service.ContestSubmissionService;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import cn.idealismxxm.onlinejudge.service.jms.producer.MessageProducer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    private ContestSubmissionService contestSubmissionService;

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
    public Integer submit(Submission submission, String username) {
        if (submission == null || submission.getProblemId() == null || StringUtils.isBlank(username)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 获取题目信息
        problemService.getProblemById(submission.getProblemId());
        try {
            // 设置提交者用户名
            submission.setUsername(username);
            // 未判题时没有其他OJ提交帐号，默认设为 -1
            submission.setRemoteAccountId(-1);
            // 未判题时没有其他OJ提交记录，默认设为 -1
            submission.setRemoteSubmissionId(-1);
            // 未判题时没有执行时间，默认设为 -1
            submission.setTime(-1);
            // 未判题时没有使用空间，默认设为 -1
            submission.setMemory(-1);
            submission.setResult(ResultEnum.QUEUING.getCode());
            // 题库提交默认全部可见
            submission.setVisibleStatus(VisibleStatusEnum.VISIBLE.getCode());
            // TODO 去除
            // 移除不可见字符
            submission.setSource(submission.getSource().replaceAll("\u200B", ""));

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
        try {
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

    // TODO page类型的方法可以用联合查询
    @Override
    public Pagination<Submission> pageSubmissionByQueryParam(QueryParam queryParam) {
        // 1. 参数校验
        if (queryParam == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 设置查询条件的map
        Map<String, Object> queryMap = new HashMap<>(16);
        queryMap.putAll(queryParam.getParam());

        // 处理 指定 submission 的主键列表的情况
        if (queryMap.get("contestId") != null) {
            if (queryMap.get("contestId") instanceof Integer) {
                Integer contestId = (Integer) queryMap.get("contestId");
                List<Integer> ids = contestSubmissionService.listSubmissionIdByContestId(contestId);
                // 如果为空，则直接返回空分页列表
                if (CollectionUtils.isEmpty(ids)) {
                    return new Pagination<>();
                }
                queryMap.put("ids", ids);
            } else {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
            }
            queryMap.remove("contestId");
        }

        // 3. 获取数据总数，并设置相关的分页信息
        Pagination<Submission> submissionPagination = new Pagination<>();
        submissionPagination.setPageSize(queryParam.getPageSize());
        try {
            Integer totalCount = submissionDao.countSubmissionByQueryMap(queryMap);
            Integer totalPage = totalCount / submissionPagination.getPageSize();
            if (totalCount % submissionPagination.getPageSize() != 0) {
                totalPage = totalPage + 1;
            }
            submissionPagination.setTotalCount(totalCount);
            submissionPagination.setTotalPage(totalPage);

            // 4. 如果查询页号超过页数，则设置当前页为最大页
            if (queryParam.getPageNum() > submissionPagination.getTotalPage()) {
                queryParam.setPageNum(submissionPagination.getTotalPage());
            }
            submissionPagination.setPageNum(queryParam.getPageNum());

            queryMap.put("offset", queryParam.getOffset());
            queryMap.put("limit", queryParam.getLimit());

            // 5. 若存在数据，则获取本页数据
            if (totalCount != 0) {
                submissionPagination.setData(submissionDao.pageSubmissionByQueryMap(queryMap));
            }
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }

        return submissionPagination;
    }
}