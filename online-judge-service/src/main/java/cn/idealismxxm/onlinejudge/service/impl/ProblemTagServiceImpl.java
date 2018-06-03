package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.ProblemTagDao;
import cn.idealismxxm.onlinejudge.service.ProblemTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 题目标签关系接口
 *
 * @author idealism
 * @date 2018/6/3
 */
@Service("problemTagService")
public class ProblemTagServiceImpl implements ProblemTagService {
    @Resource
    private ProblemTagDao problemTagDao;

}