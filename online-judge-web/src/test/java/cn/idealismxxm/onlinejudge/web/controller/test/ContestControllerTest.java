package cn.idealismxxm.onlinejudge.web.controller.test;

import cn.idealismxxm.onlinejudge.domain.entity.Contest;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.PublicStatusEnum;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ContestController 测试类
 *
 * @author idealism
 * @date 2018/5/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 必须加载 mvc 配置文件，否则无法正常处理请求
@ContextConfiguration({"classpath:spring/spring-config.xml", "classpath:spring/spring-config-mvc.xml"})
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
@WebAppConfiguration
public class ContestControllerTest {
    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addContestTest() {
        Contest contest = this.initContest();
        try {
            String responseString = mockMvc.perform(post("/contest/addContest")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("contestJson", JsonUtil.objectToJson(contest))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editContestTest() {
        Contest contest = this.initContest();
        contest.setId(1);
        try {
            String responseString = mockMvc.perform(post("/contest/editContest")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("contestJson", JsonUtil.objectToJson(contest))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listTest() {
        Map<String, Object> queryParam = this.initQueryParam();
        try {
            String responseString = mockMvc.perform(get("/contest/list")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("queryParamJson", JsonUtil.objectToJson(queryParam))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTest() {
        Integer contestId = 1;
        try {
            String responseString = mockMvc.perform(get("/contest/get")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("contestId", contestId.toString())
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化测试用的比赛信息
     *
     * @return 比赛信息
     */
    private Contest initContest() {
        Contest contest = new Contest();

        contest.setName("比赛");
        contest.setStartTime(new Date(System.currentTimeMillis() + 1000 * 60 * 6));
        contest.setEndTime(new Date(System.currentTimeMillis() + 1000 * 60 * 6 + 1000 * 60 * 60 * 2));
        contest.setCreator("admin");
        contest.setProblemIds("[57]");

        return contest;
    }

    /**
     * 初始化测试用的查询条件
     *
     * @return 查询条件
     */
    private Map<String, Object> initQueryParam() {
        Map<String, Object> queryParam = new HashMap<>(5);
        queryParam.put("pageNum", 1);
        queryParam.put("pageSize", 20);
        Map<String, Object> param = new HashMap<>(2);
        param.put("name", "比赛");
        param.put("creator", "admin");
        queryParam.put("param", param);

        return queryParam;
    }
}
