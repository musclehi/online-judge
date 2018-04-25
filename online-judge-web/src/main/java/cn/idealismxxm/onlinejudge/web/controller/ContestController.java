package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.entity.Contest;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.ContestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 比赛相关操作
 *
 * @author idealism
 * @date 2018/4/24
 */
@Controller
@RequestMapping("contest")
public class ContestController {
    @Resource
    private ContestService contestService;

    /**
     * 添加新比赛
     *
     * @param contestJson 比赛实例json
     * @return 比赛的id
     */
    @ResponseBody
    @RequestMapping(value = "addContest", method = {RequestMethod.POST})
    public AjaxResult<Integer> addContest(String contestJson) {
        Contest contest = JsonUtil.jsonToObject(contestJson, Contest.class);
        Integer id = contestService.addContest(contest);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }
}
