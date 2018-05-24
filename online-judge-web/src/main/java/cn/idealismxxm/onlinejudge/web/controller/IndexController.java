package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 初始控制器
 *
 * @author idealism
 * @date 2018/3/22
 */
@Controller
public class IndexController {

    @RequirePrivilege
    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
