package com.huifer.restsec;

import com.huifer.restsec.entity.dto.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestSecApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/hello")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(10))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void findById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/123")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("123"))
                .andDo(MockMvcResultHandlers.print());


    }


    @Test
    public void findByIdError() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/df")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)

        ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());


        System.out.println();
    }


    /**
     * 没有添加 @RequestBody 注解可以使用这样的
     * @throws Exception
     */
    @Test
    public void addUser()throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("name", "zhangsan")
                        .param("pwd", "123")
        ).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 使用@RequestBody
     * @throws Exception
     */
    @Test
    public void addUser2()throws Exception{
        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setPwd("123");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                     .content(
                             userInfo.toString()
                     )
        ).andDo(MockMvcResultHandlers.print());
    }

}
