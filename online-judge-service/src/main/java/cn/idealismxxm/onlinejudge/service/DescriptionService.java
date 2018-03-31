package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Description;

/**
 * 题目描述相关操作接口
 *
 * @author idealism
 * @date 2018/3/30
 */
public interface DescriptionService {
    /**
     * 通过 题目描述id 返回题目描述实例
     *
     * @param descriptionId 题目描述id
     * @return 题目描述实例
     */
    Description getDescriptionById(Integer descriptionId);
}