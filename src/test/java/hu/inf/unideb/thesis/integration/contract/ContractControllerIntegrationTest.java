package hu.inf.unideb.thesis.integration.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.service.ContractService;
import hu.inf.unideb.thesis.service.InstitutionService;
import hu.inf.unideb.thesis.service.RoleService;
import hu.inf.unideb.thesis.service.UserService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class ContractControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @BeforeEach
    public void setUp(){
        roleService.setUpMockedData();
        userService.setUpMockedData();
        institutionService.setUpMockedData();
        contractService.setUpMockedData();
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testGetContracts() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/contracts").accept(MediaType.APPLICATION_JSON)
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
    @WithMockUser(authorities = "USER")
    public void testGetContractById() throws Exception{

        Contract temp = contractService.findAll().get(0);

        MvcResult mvcResult = mockMvc.perform(get("/contracts/{id}",temp.getId()).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.student.username").value("user"));

    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testCreateContractThenDeleteContract() throws Exception{

        Contract testContract = new Contract();
        testContract.setId(2L);
        testContract.setCompleted(true);

        MvcResult mvcResult = mockMvc.perform(post("/contracts").with(csrf())
                        .content(objectMapper.writeValueAsString(testContract))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        Contract temp = contractService.findById(2L);

        MvcResult mvcResult2 = mockMvc.perform(delete("/contracts/{id}", temp.getId()).with(csrf())
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
    public void testUpdateContract() throws Exception{

        Contract testContract  = new Contract();

        testContract.setId(3L);
        testContract.setCompleted(false);

        MvcResult mvcResult = mockMvc.perform(post("/contracts").with(csrf())
                        .content(objectMapper.writeValueAsString(testContract))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        Contract temp = contractService.findById(3L);

        temp.setCompleted(true);

        MvcResult mvcResult2 = mockMvc.perform(put("/contracts/{id}",temp.getId()).with(csrf())
                        .content(objectMapper.writeValueAsString(testContract))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult2))
                .andExpect(status().is(204))
                .andDo(print());
    }
}
