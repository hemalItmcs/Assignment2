package com.example.demo;

import com.example.telegram.TelegramApplication;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelegramApplication.class)
@WebAppConfiguration
public class TelegramApplicationTests {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

   

    @Test
    public void testUserSaveWithSuccessStatus() throws Exception {
        String username = "Narendra Modi";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/save")
                .param("username", username);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.OK.value())));
    }
    
    @Test
    public void testUserSaveWithRequiredUsernameStatus() throws Exception {
        String username = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/save")
                .param("username", username);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.BAD_REQUEST.value())));
    }
    
    @Test
    public void testChatbotoptionWithUserNameRequireStatus() throws Exception {
        String username = "";
        String location = "india";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chatbot/1")
                .param("username", username)
                .param("location", location);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.BAD_REQUEST.value())));
    }
    
    @Test
    public void testChatbotoptionWithLocationRequireStatus() throws Exception {
        String username = "Mohit Suri";
        String location = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chatbot/1")
                .param("username", username)
                .param("location", location);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.BAD_REQUEST.value())));
    }
    
    @Test
    public void testChatbotoptionWithInvalidOperationStatus() throws Exception {
        String username = "Mohit Suri";
        String location = "India";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chatbot/0")
                .param("username", username)
                .param("location", location);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.FORBIDDEN.value())));
    }
    
    @Test
    public void testChatbotoptionWithSuccessOfWeatherOption() throws Exception {
        String username = "Mohit Suri";
        String location = "India";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chatbot/1")
                .param("username", username)
                .param("location", location);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.OK.value())));
    }
    
    @Test
    public void testChatbotoptionWithSuccessOfNewsOption() throws Exception {
        String username = "Mohit Suri";
        String location = "India";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chatbot/2")
                .param("username", username)
                .param("location", location);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.OK.value())));
    }
    
    @Test
    public void testChatbotoptionWithSuccessOfRestaurnatOption() throws Exception {
        String username = "Mohit Suri";
        String location = "Maninagar";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chatbot/3")
                .param("username", username)
                .param("location", location);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus(), anyOf(is(HttpStatus.OK.value())));
    }

}   
