package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.OriginalProblemDao;
import cn.idealismxxm.onlinejudge.entity.OriginalProblem;
import cn.idealismxxm.onlinejudge.service.OriginalProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OriginalProblemServiceImpl implements OriginalProblemService {
    @Resource
    private OriginalProblemDao originalProblemDao;

    @Override
    public long getOriginalProblemRowCount() {
        return originalProblemDao.getOriginalProblemRowCount();
    }

    @Override
    public List<OriginalProblem> selectOriginalProblem() {
        return originalProblemDao.selectOriginalProblem();
    }

    @Override
    public OriginalProblem selectOriginalProblemByObj(OriginalProblem obj) {
        return originalProblemDao.selectOriginalProblemByObj(obj);
    }

    @Override
    public OriginalProblem selectOriginalProblemById(Integer id) {
        return originalProblemDao.selectOriginalProblemById(id);
    }

    @Override
    public int insertOriginalProblem(OriginalProblem value) {
        return originalProblemDao.insertOriginalProblem(value);
    }

    @Override
    public int insertNonEmptyOriginalProblem(OriginalProblem value) {
        return originalProblemDao.insertNonEmptyOriginalProblem(value);
    }

    @Override
    public int insertOriginalProblemByBatch(List<OriginalProblem> value) {
        return originalProblemDao.insertOriginalProblemByBatch(value);
    }

    @Override
    public int deleteOriginalProblemById(Integer id) {
        return originalProblemDao.deleteOriginalProblemById(id);
    }

    @Override
    public int updateOriginalProblemById(OriginalProblem enti) {
        return originalProblemDao.updateOriginalProblemById(enti);
    }

    @Override
    public int updateNonEmptyOriginalProblemById(OriginalProblem enti) {
        return originalProblemDao.updateNonEmptyOriginalProblemById(enti);
    }

    public OriginalProblemDao getOriginalProblemDao() {
        return this.originalProblemDao;
    }

    public void setOriginalProblemDao(OriginalProblemDao originalProblemDao) {
        this.originalProblemDao = originalProblemDao;
    }

}