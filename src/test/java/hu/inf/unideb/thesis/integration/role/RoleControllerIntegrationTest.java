package hu.inf.unideb.thesis.integration.role;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.service.RoleService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RoleService roleService;

    @BeforeEach
    public void setUp(){
        roleService.setUpMockedData();
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testGetRoles() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/roles").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(responseBody);

        int count = jsonNode.size();

        assertEquals(2, count);
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testGetRoleById()throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/roles/{id}", 1L).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String name = jsonNode.get("name").toPrettyString().substring(1,11);

        assertEquals("BACKOFFICE", name);
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testCreateRoleThenDelete() throws Exception {

        Role testRole = new Role();
        testRole.setId(5L);
        testRole.setName("integrationTestRole");



        MvcResult mvcResult = mockMvc.perform(post("/roles").with(csrf())
                        .content(objectMapper.writeValueAsString(testRole))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        Role temp = roleService.findByName("integrationTestRole");

        MvcResult mvcResult2 = mockMvc.perform(delete("/roles/{id}", temp.getId()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult2))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testUpdateRole() throws Exception {

        Role testRole = new Role();
        testRole.setId(4L);
        testRole.setName("integrationTestRoleForUpdate");

        roleService.save(testRole);

        Role temp = roleService.findByName("integrationTestRoleForUpdate");

        temp.setName("integrationTestRoleForUpdate2");

        MvcResult mvcResult = mockMvc.perform(put("/roles/{id}",temp.getId()).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(temp))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().is(204))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("integrationTestRoleForUpdate2"));

    }
}
