package cn.idealismxxm.onlinejudge.service.impl;

import java.util.List;

import cn.idealismxxm.onlinejudge.dao.ProblemDao;
import cn.idealismxxm.onlinejudge.entity.Problem;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ProblemDao problemDao;

    @Override
    public long getProblemRowCount() {
        return problemDao.getProblemRowCount();
    }

    @Override
    public List<Problem> selectProblem() {
        return problemDao.selectProblem();
    }

    @Override
    public Problem selectProblemByObj(Problem obj) {
        return problemDao.selectProblemByObj(obj);
    }

    @Override
    public Problem selectProblemById(Integer id) {
        return problemDao.selectProblemById(id);
    }

    @Override
    public int insertProblem(Problem value) {
        return problemDao.insertProblem(value);
    }

    @Override
    public int insertNonEmptyProblem(Problem value) {
        return problemDao.insertNonEmptyProblem(value);
    }

    @Override
    public int insertProblemByBatch(List<Problem> value) {
        return problemDao.insertProblemByBatch(value);
    }

    @Override
    public int deleteProblemById(Integer id) {
        return problemDao.deleteProblemById(id);
    }

    @Override
    public int updateProblemById(Problem enti) {
        return problemDao.updateProblemById(enti);
    }

    @Override
    public int updateNonEmptyProblemById(Problem enti) {
        return problemDao.updateNonEmptyProblemById(enti);
    }

    public ProblemDao getProblemDao() {
        return this.problemDao;
    }

    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

}