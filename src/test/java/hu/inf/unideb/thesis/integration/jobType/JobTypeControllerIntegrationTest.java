package hu.inf.unideb.thesis.integration.jobType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.service.JobTypeService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class JobTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobTypeService jobTypeService;

    @BeforeEach
    private void setUp(){
        jobTypeService.setUpMockedData();
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testGetJobtypes() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/jobTypes").accept(MediaType.APPLICATION_JSON)
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

        assertTrue(count > 0);
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testGetJobTypeById() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/jobTypes/{id}",1L).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    @WithMockUser(authorities = "BACKOFFICE")
    public void testCreateJobTypeThenDeleteJobType() throws Exception{

        JobType integrationTestType = new JobType();
        integrationTestType.setId(7L);
        integrationTestType.setName("integrationCreationTestJobType");

        MvcResult mvcResult = mockMvc.perform(post("/jobTypes").with(csrf())
                        .content(objectMapper.writeValueAsString(integrationTestType))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        JobType temp = jobTypeService.findByName("integrationCreationTestJobType");

        MvcResult mvcResult2 = mockMvc.perform(delete("/jobTypes/{id}", temp.getId()).with(csrf())
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
    public void testUpdateJobType() throws Exception{

        JobType test = jobTypeService.findByName("test3");

        test.setName("ingerationTestType");

        MvcResult mvcResult = mockMvc.perform(put("/jobTypes/{id}",test.getId()).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(test))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().is(204))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("ingerationTestType"));


    }
}
