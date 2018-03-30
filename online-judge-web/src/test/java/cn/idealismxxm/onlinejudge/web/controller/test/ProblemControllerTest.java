package cn.idealismxxm.onlinejudge.web.controller.test;

import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.enums.OnlineJudgeEnum;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ProblemController 测试类
 *
 * @author idealism
 * @date 2018/3/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 必须加载 mvc 配置文件，否则无法正常处理请求
@ContextConfiguration({"classpath:spring/spring-config.xml", "classpath:spring/spring-config-mvc.xml"})
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
@WebAppConfiguration
public class ProblemControllerTest {
    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addTest() {
        Problem problem = this.initProblem();
        Description description = this.initDescription();
        try {
            String responseString = mockMvc.perform(post("/problem/add")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemJson", JsonUtil.objectToJson(problem))
                    .param("descriptionJson", JsonUtil.objectToJson(description))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editTest() {
        Problem problem = this.initProblem();
        Description description = this.initDescription();
        try {
            String responseString = mockMvc.perform(post("/problem/edit")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemJson", JsonUtil.objectToJson(problem))
                    .param("descriptionJson", JsonUtil.objectToJson(description))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化测试用的题目
     *
     * @return 题目
     */
    private Problem initProblem() {
        Problem problem = new Problem();
        problem.setOriginalOj(OnlineJudgeEnum.THIS.getCode());
        problem.setOriginalId("");
        problem.setPublicStatus(PublicStatusEnum.PRIVATE.getCode());
        problem.setTitle("题目标题");
        problem.setTimeLimit(2000);
        problem.setMemoryLimit(65535);
        problem.setTag("");
        problem.setUrl(OnlineJudgeEnum.THIS.getUrl(""));

        return problem;
    }

    /**
     * 初始化测试用的题目描述
     *
     * @return 题目描述
     */
    private Description initDescription() {
        Description description = new Description();
        description.setDescription("题目描述");
        description.setInput("输入");
        description.setOutput("输出");
        description.setSampleInput("输入样例");
        description.setSampleOutput("输出样例");
        description.setExtension("{}");

        return description;
    }
}
