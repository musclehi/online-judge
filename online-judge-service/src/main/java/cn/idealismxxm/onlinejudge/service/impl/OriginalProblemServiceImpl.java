package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.OriginalProblemDao;
import cn.idealismxxm.onlinejudge.dao.ProblemDao;
import cn.idealismxxm.onlinejudge.entity.OriginalProblem;
import cn.idealismxxm.onlinejudge.entity.Problem;
import cn.idealismxxm.onlinejudge.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.enums.PublicStatusEnum;
import cn.idealismxxm.onlinejudge.service.OriginalProblemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Service("originalProblemService")
public class OriginalProblemServiceImpl implements OriginalProblemService {

    @Resource
    private OriginalProblemDao originalProblemDao;

    @Resource
    private ProblemDao problemDao;


    @Override
    @Transactional(isolation = READ_COMMITTED)
    public Integer addOriginalProblem(OriginalProblem originalProblem) {
        // 1. 插入原创题目，并得到其id
        originalProblemDao.insertOriginalProblem(originalProblem);
        Integer originalProblemId = originalProblem.getId();

        // 2. 初始化题目对象，并插入数据库
        Problem problem = this.initProblem(originalProblem);
        problem.setPublicStatus(PublicStatusEnum.PRIVATE.getCode());
        problem.setOriginalId(originalProblemId.toString());
        problem.setUrl(OnlineJudgeEnum.ORIGINAL.getUrl(originalProblemId.toString()));

        problemDao.insertProblem(problem);

        return originalProblem.getId();
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public Boolean editOriginalProblemById(OriginalProblem originalProblem) {
        // 1. 更新原创题目
        originalProblemDao.updateOriginalProblemById(originalProblem);

        // 2. 初始化题目对象，并更新数据库
        Problem problem = this.initProblem(originalProblem);
        problemDao.updateNonEmptyProblemById(problem);

        return true;
    }

    /**
     * 根据原创题目初始化题目对象
     *
     * @param originalProblem 原创题目
     * @return 题目对象
     */
    private Problem initProblem(OriginalProblem originalProblem) {
        Problem problem = new Problem();
        problem.setOriginalOj(OnlineJudgeEnum.ORIGINAL.getCode());
        problem.setTitle(originalProblem.getTitle());
        problem.setTimeLimit(originalProblem.getTimeLimit());
        problem.setMemoryLimit(originalProblem.getMemoryLimit());

        return problem;
    }
}