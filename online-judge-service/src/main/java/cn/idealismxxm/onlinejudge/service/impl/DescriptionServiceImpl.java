package cn.idealismxxm.onlinejudge.service.impl;
import java.util.List;
import cn.idealismxxm.onlinejudge.dao.DescriptionDao;
import cn.idealismxxm.onlinejudge.entity.Description;
import cn.idealismxxm.onlinejudge.service.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DescriptionServiceImpl implements DescriptionService{
    @Autowired
    private DescriptionDao descriptionDao;
    @Override
    public long getDescriptionRowCount(){
        return descriptionDao.getDescriptionRowCount();
    }
    @Override
    public List<Description> selectDescription(){
        return descriptionDao.selectDescription();
    }
    @Override
    public Description selectDescriptionByObj(Description obj){
        return descriptionDao.selectDescriptionByObj(obj);
    }
    @Override
    public Description selectDescriptionById(Integer id){
        return descriptionDao.selectDescriptionById(id);
    }
    @Override
    public int insertDescription(Description value){
        return descriptionDao.insertDescription(value);
    }
    @Override
    public int insertNonEmptyDescription(Description value){
        return descriptionDao.insertNonEmptyDescription(value);
    }
    @Override
    public int insertDescriptionByBatch(List<Description> value){
        return descriptionDao.insertDescriptionByBatch(value);
    }
    @Override
    public int deleteDescriptionById(Integer id){
        return descriptionDao.deleteDescriptionById(id);
    }
    @Override
    public int updateDescriptionById(Description enti){
        return descriptionDao.updateDescriptionById(enti);
    }
    @Override
    public int updateNonEmptyDescriptionById(Description enti){
        return descriptionDao.updateNonEmptyDescriptionById(enti);
    }

    public DescriptionDao getDescriptionDao() {
        return this.descriptionDao;
    }

    public void setDescriptionDao(DescriptionDao descriptionDao) {
        this.descriptionDao = descriptionDao;
    }

}