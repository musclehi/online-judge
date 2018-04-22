package cn.idealismxxm.onlinejudge.web.controller.test;

import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserController 测试类
 *
 * @author idealism
 * @date 2018/4/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 必须加载 mvc 配置文件，否则无法正常处理请求
@ContextConfiguration({"classpath:spring/spring-config.xml", "classpath:spring/spring-config-mvc.xml"})
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
@WebAppConfiguration
public class UserControllerTest {
    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void signUpTest() {
        User user = this.initUser();
        try {
            String responseString = mockMvc.perform(post("/user/signUp")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("userJson", JsonUtil.objectToJson(user))
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void signInTest() {
        User user = this.initUser();
        try {
            MvcResult result = mockMvc.perform(post("/user/signIn")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .param("account", user.getUsername())
                    .param("password", user.getPassword())
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            System.out.println(JsonUtil.objectToJson(result.getRequest().getSession().getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化测试用的用户实例
     *
     * @return 用户实例
     */
    private User initUser() {
        User user = new User();

        user.setUsername("admin");
        user.setNickname("管理员");
        user.setPassword("Pa55word");
        user.setEmail("admin@163.com");
        return user;
    }
}
