package hu.inf.unideb.thesis.integration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;


    @BeforeEach
    public void setUp(){
        userService.setUpMockedData();
    }
    @Test
    @WithMockUser(authorities = "USER")
    public void testGetUsers() throws Exception {

       MvcResult mvcResult = mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
                       .contentType(MediaType.APPLICATION_JSON))
                       .andExpect(request().asyncStarted())
                       .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content").exists());


    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testGetUserById() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/users/{id}",1L).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username").value("user"));

    }
    @Test
    @WithMockUser(authorities = "USER")
    @Transactional
    public void testRegisterThenDeleteUser() throws Exception {
        User user2 = new User();
        user2.setId(3L);
        user2.setUsername("testRegisteringUser");
        user2.setPassword("testpassword123");
        user2.setEmail("test@example.com");

        MvcResult mvcResult = mockMvc.perform(post("/users/signup").with(csrf())
                        .content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        MvcResult mvcResult2 = mockMvc.perform(delete("/users/{id}", 3L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult2))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "USER")
    @Transactional
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setUsername("testCreateuser2");
        user.setPassword("testasdawq221223");
        user.setEmail("test@example.com");

        MvcResult mvcResult = mockMvc.perform(post("/users").with(csrf())
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.username").value("testCreateuser2"));

        user.setEmail("newTestEmail@gmail.com");

        MvcResult mvcResult2 = mockMvc.perform(put("/users/{id}", 1L).with(csrf())
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult2))
                .andExpect(status().is(204))
                .andDo(print());
    }

}
