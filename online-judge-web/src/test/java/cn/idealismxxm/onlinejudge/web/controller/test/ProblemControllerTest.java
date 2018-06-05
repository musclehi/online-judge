package cn.idealismxxm.onlinejudge.web.controller.test;

import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;
import cn.idealismxxm.onlinejudge.domain.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PublicStatusEnum;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        List<TestCase> testCases = this.initTestCases();
        try {
            String responseString = mockMvc.perform(post("/problem/add")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemJson", JsonUtil.objectToJson(problem))
                    .param("descriptionJson", JsonUtil.objectToJson(description))
                    .param("testCasesJson", JsonUtil.objectToJson(testCases))
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
        problem.setId(57);
        Description description = this.initDescription();
        List<TestCase> testCases = this.initTestCases();
        try {
            String responseString = mockMvc.perform(post("/problem/edit")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemJson", JsonUtil.objectToJson(problem))
                    .param("descriptionJson", JsonUtil.objectToJson(description))
                    .param("testCasesJson", JsonUtil.objectToJson(testCases))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllInfoTest() {
        Integer problemId = 57;
        try {
            String responseString = mockMvc.perform(get("/problem/getAllInfo")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemId", problemId.toString())
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
            String responseString = mockMvc.perform(get("/problem/list")
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
        Integer problemId = 57;
        try {
            String responseString = mockMvc.perform(get("/problem/get")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemId", problemId.toString())
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listTagByProblemIdTest() {
        Integer problemId = 57;
        try {
            String responseString = mockMvc.perform(get("/problem/listTagByProblemId")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemId", problemId.toString())
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addProblemTagTest() {
        Integer problemId = 57;
        Integer tagId = 1;
        try {
            String responseString = mockMvc.perform(post("/problem/addProblemTag")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemId", problemId.toString())
                    .param("tagId", tagId.toString())
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteProblemTagTest() {
        Integer problemTagId = 1;
        try {
            String responseString = mockMvc.perform(post("/problem/addProblemTag")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("problemTagId", problemTagId.toString())
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

    /**
     * 初始化测试用的测试用例列表
     *
     * @return 测试用例列表
     */
    private List<TestCase> initTestCases() {
        int length = 2;
        int IdOffset = 5;
        List<TestCase> testCases = new ArrayList<>(length);
        for(int i = 1; i <= length; ++i) {
            TestCase testCase = new TestCase();
            testCase.setId(IdOffset + i - 1);
            testCase.setInput(i + " " + (i + 1));
            testCase.setOutput((i << 1) + 1 + "");
            testCase.setScore(1);

            testCases.add(testCase);
        }

        return testCases;
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
        param.put("title", "标题");
        param.put("tagId", 1);
        queryParam.put("param", param);

        return queryParam;
    }
}
