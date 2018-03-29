package cn.idealismxxm.onlinejudge.controller.test;

import cn.idealismxxm.onlinejudge.entity.OriginalProblem;
import cn.idealismxxm.onlinejudge.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OriginalProblemController 测试类
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
public class OriginalProblemControllerTest {
    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addTest() {
        OriginalProblem originalProblem = initOriginalProblem();
        try {
            String originalProblemJson = JsonUtil.ObjectToJson(originalProblem);
            MvcResult responseString = mockMvc.perform(post("/originalProblem/add")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("originalProblemJson", originalProblemJson)
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化测试用的原创题目
     *
     * @return 原创题目
     */
    private OriginalProblem initOriginalProblem() {
        OriginalProblem originalProblem = new OriginalProblem();
        originalProblem.setTitle("题目标题");
        originalProblem.setDescription("题目描述");
        originalProblem.setTimeLimit(2000);
        originalProblem.setMemoryLimit(65535);
        originalProblem.setInput("输入");
        originalProblem.setOutput("输出");
        originalProblem.setSampleInput("输入样例");
        originalProblem.setSampleOutput("输出样例");
        originalProblem.setExtension("{}");

        return originalProblem;
    }
}
