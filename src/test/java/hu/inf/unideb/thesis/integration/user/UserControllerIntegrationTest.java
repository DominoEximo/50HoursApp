package hu.inf.unideb.thesis.integration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.GeocodingService;
import hu.inf.unideb.thesis.service.RoleService;
import hu.inf.unideb.thesis.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetUsers() throws Exception {

       userService.setUpMockedData();


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
    @WithMockUser(roles = "USER")
    @Transactional
    public void testRegisterThanDeleteUser() throws Exception {
        User user2 = new User();
        user2.setId(3L);
        user2.setUsername("testRegisteringUser");
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
    @WithMockUser(roles = "USER")
    @Transactional
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setUsername("testCreateuser2");
        user.setEmail("test@example.com");

        MvcResult mvcResult = mockMvc.perform(post("/users").with(csrf())
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                // Add additional assertions as needed to verify the response
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
