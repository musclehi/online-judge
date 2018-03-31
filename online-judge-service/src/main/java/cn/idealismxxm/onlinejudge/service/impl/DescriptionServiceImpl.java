package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.DescriptionDao;
import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.DescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 题目描述相关操作接口实现
 *
 * @author idealism
 * @date 2018/3/30
 */
@Service("descriptionService")
public class DescriptionServiceImpl implements DescriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionServiceImpl.class);

    @Resource
    private DescriptionDao descriptionDao;

    @Override
    public Description getDescriptionById(Integer descriptionId) {
        // 参数校验
        if (descriptionId == null || descriptionId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 读库
        Description description;
        try {
            description = descriptionDao.selectDescriptionById(descriptionId);
        } catch (Exception e) {
            LOGGER.error("#getDescriptionById error, descriptionId: {}", descriptionId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }

        // 验证数据是否存在
        if (description == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.SUBMISSION_NOT_EXIST);
        }
        return description;
    }
}