package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.ContestDao;
import cn.idealismxxm.onlinejudge.domain.entity.Contest;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;
import cn.idealismxxm.onlinejudge.domain.util.RequestUtil;
import cn.idealismxxm.onlinejudge.service.ContestService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    public Contest getContestById(Integer id) {
        if(id == null || id <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        Contest contest;
        try {
            contest = contestDao.selectContestById(id);
        } catch (Exception e) {
            LOGGER.error("#getContestById error, id: {}", id, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        if(contest == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.CONTEST_NOT_EXIST);
        }
        return contest;
    }

    @Override
    public Integer addContest(Contest contest) {
        // 参数校验
        this.validate(contest);

        // 开始时间至少比当前时间晚：CommonConstant.CONTEST_MIN_START_INTERVAL
        long startInterval = contest.getStartTime().getTime() - System.currentTimeMillis();
        if(startInterval < CommonConstant.CONTEST_MIN_START_INTERVAL) {
            throw BusinessException.buildCustomizedMessageException(String.format("开始时间至少比当前时间晚%d分钟", CommonConstant.CONTEST_MIN_START_INTERVAL / 1000 / 60));
        }

        try {
            contest.setCreator(((User) RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER)).getUsername());
            contestDao.insertContest(contest);
            return contest.getId();
        } catch (Exception e) {
            LOGGER.error("#addContest error, contest: {}", JsonUtil.objectToJson(contest), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public Boolean editContest(Contest contest) {
        this.validate(contest);

        Contest oldContest = this.getContestById(contest.getId());
        // 比赛已开始，则不允许更改开始时间
        if(oldContest.getStartTime().getTime() <= System.currentTimeMillis()) {
            if(oldContest.getStartTime().getTime() != contest.getStartTime().getTime()) {
                throw BusinessException.buildCustomizedMessageException("比赛已开始，无法修改开始时间！");
            }
        } // 比赛未开始，且开始时间修改，则至少比当前时间晚：CommonConstant.CONTEST_MIN_START_INTERVAL
        else if(oldContest.getStartTime().getTime() != contest.getStartTime().getTime()) {
            long startInterval = contest.getStartTime().getTime() - System.currentTimeMillis();
            if (startInterval < CommonConstant.CONTEST_MIN_START_INTERVAL) {
                throw BusinessException.buildCustomizedMessageException(String.format("开始时间至少比当前时间晚%d分钟", CommonConstant.CONTEST_MIN_START_INTERVAL / 1000 / 60));
            }
        }

        try {
            contestDao.updateNonEmptyContestById(contest);
            return true;
        } catch (Exception e) {
            LOGGER.error("#editContest error, contest: {}", JsonUtil.objectToJson(contest), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public Pagination<Contest> pageContestByQueryParam(QueryParam queryParam) {
        // 1. 参数校验
        if(queryParam == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 设置查询条件的map
        Map<String, Object> queryMap = new HashMap<>(16);
        queryMap.putAll(queryParam.getParam());

        // 3. 获取数据总数，并设置相关的分页信息
        Pagination<Contest> contestPagination = new Pagination<>();
        contestPagination.setPageSize(queryParam.getPageSize());
        Integer totalCount = contestDao.countContestByQueryMap(queryMap);
        Integer totalPage = totalCount / contestPagination.getPageSize();
        if(totalCount % contestPagination.getPageSize() != 0) {
            totalPage = totalPage + 1;
        }
        contestPagination.setTotalCount(totalCount);
        contestPagination.setTotalPage(totalPage);

        // 4. 如果查询页号超过页数，则设置当前页为最大页
        if(queryParam.getPageNum() > contestPagination.getTotalPage()) {
            queryParam.setPageNum(contestPagination.getTotalPage());
        }
        contestPagination.setPageNum(queryParam.getPageNum());

        queryMap.put("offset", queryParam.getOffset());
        queryMap.put("limit", queryParam.getLimit());

        // 5. 若存在数据，则获取本页数据
        if(totalCount != 0) {
            contestPagination.setData(contestDao.pageContestByQueryMap(queryMap));
        }

        return contestPagination;
    }

    /**
     * 校验比赛信息是否合法
     * @param contest 比赛实例
     */
    private void validate(Contest contest) {
        if(contest == null || StringUtils.isBlank(contest.getName()) || contest.getStartTime() == null || contest.getEndTime() == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        long duration = contest.getEndTime().getTime() - contest.getStartTime().getTime();
        if(duration < CommonConstant.CONTEST_MIN_DURATION) {
            throw BusinessException.buildCustomizedMessageException(String.format("比赛最少持续%d小时", CommonConstant.CONTEST_MIN_DURATION / 1000 / 60 / 60));
        }
    }
}