package cn.idealismxxm.onlinejudge.web.controller.test;

import cn.idealismxxm.onlinejudge.domain.entity.Tag;
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
 * TagController 测试类
 *
 * @author idealism
 * @date 2018/6/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 必须加载 mvc 配置文件，否则无法正常处理请求
@ContextConfiguration({"classpath:spring/spring-config.xml", "classpath:spring/spring-config-mvc.xml"})
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
@WebAppConfiguration
public class TagControllerTest {
    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addTagTest() {
        Tag tag = this.initTag();
        try {
            String responseString = mockMvc.perform(post("/tag/addTag")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("tagJson", JsonUtil.objectToJson(tag))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editTagTest() {
        Tag tag = this.initTag();
        tag.setId(1);
        try {
            String responseString = mockMvc.perform(post("/tag/editTag")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("tagJson", JsonUtil.objectToJson(tag))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTagTest() {
        Integer tagId = 1;
        try {
            String responseString = mockMvc.perform(post("/tag/deleteTag")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
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
    public void pageTagTest() {
        Map<String, Object> queryParam = this.initQueryParam();
        try {
            String responseString = mockMvc.perform(get("/tag/pageTag")
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
    public void getTagTest() {
        Integer tagId = 1;
        try {
            String responseString = mockMvc.perform(get("/tag/getTag")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("tagId", tagId.toString())
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化测试用的标签
     *
     * @return 标签
     */
    private Tag initTag() {
        Tag tag = new Tag();

        tag.setParentId(0);
        tag.setName("标签");

        return tag;
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
        param.put("name", "标签");
        param.put("parentId", 0);
        queryParam.put("param", param);

        return queryParam;
    }
}
