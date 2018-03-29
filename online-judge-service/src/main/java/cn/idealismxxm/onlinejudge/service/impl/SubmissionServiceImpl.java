package cn.idealismxxm.onlinejudge.service.impl;
import java.util.List;
import cn.idealismxxm.onlinejudge.dao.SubmissionDao;
import cn.idealismxxm.onlinejudge.entity.Submission;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SubmissionServiceImpl implements SubmissionService{
    @Autowired
    private SubmissionDao submissionDao;
    @Override
    public long getSubmissionRowCount(){
        return submissionDao.getSubmissionRowCount();
    }
    @Override
    public List<Submission> selectSubmission(){
        return submissionDao.selectSubmission();
    }
    @Override
    public Submission selectSubmissionByObj(Submission obj){
        return submissionDao.selectSubmissionByObj(obj);
    }
    @Override
    public Submission selectSubmissionById(Integer id){
        return submissionDao.selectSubmissionById(id);
    }
    @Override
    public int insertSubmission(Submission value){
        return submissionDao.insertSubmission(value);
    }
    @Override
    public int insertNonEmptySubmission(Submission value){
        return submissionDao.insertNonEmptySubmission(value);
    }
    @Override
    public int insertSubmissionByBatch(List<Submission> value){
        return submissionDao.insertSubmissionByBatch(value);
    }
    @Override
    public int deleteSubmissionById(Integer id){
        return submissionDao.deleteSubmissionById(id);
    }
    @Override
    public int updateSubmissionById(Submission enti){
        return submissionDao.updateSubmissionById(enti);
    }
    @Override
    public int updateNonEmptySubmissionById(Submission enti){
        return submissionDao.updateNonEmptySubmissionById(enti);
    }

    public SubmissionDao getSubmissionDao() {
        return this.submissionDao;
    }

    public void setSubmissionDao(SubmissionDao submissionDao) {
        this.submissionDao = submissionDao;
    }

}