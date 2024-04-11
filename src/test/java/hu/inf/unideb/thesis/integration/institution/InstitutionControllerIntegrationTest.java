package hu.inf.unideb.thesis.integration.institution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.Location;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class InstitutionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @BeforeEach
    public void setUp(){
        roleService.setUpMockedData();
        userService.setUpMockedData();
        institutionService.setUpMockedData();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetInstitutions() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get("/institutions").accept(MediaType.APPLICATION_JSON)
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
    public void testCreateInstitutionsThenDelete() throws Exception{

        Institution institution = new Institution();
        institution.setId(5L);
        institution.setName("integrationTestCase");
        Location testLocation = new Location();
        testLocation.setName("testLoc");
        testLocation.setCountry("Hungary");
        testLocation.setStreet("Nyíregyháza");
        institution.setLocation(testLocation);

        MvcResult mvcResult = mockMvc.perform(post("/institutions").with(csrf())
                        .content(objectMapper.writeValueAsString(institution))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        MvcResult mvcResult2 = mockMvc.perform(delete("/institutions/{id}", 2L).with(csrf())
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
    public void testGetInstitutionById() throws Exception{

        Institution temp = institutionService.findByName("TestInstitution");

        MvcResult mvcResult = mockMvc.perform(get("/institutions/{id}",temp.getId()).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("TestInstitution"));

    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdateInstitution() throws Exception{

        Institution temp = institutionService.findByName("TestInstitution");
        Location testLocation = new Location();
        testLocation.setName("instUpdateTestLoc");
        testLocation.setCountry("Germany");
        temp.setLocation(testLocation);

        MvcResult mvcResult = mockMvc.perform(put("/institutions/{id}",temp.getId()).with(csrf())
                        .content(objectMapper.writeValueAsString(temp))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().is(204))
                .andDo(print());


    }
    @Test
    @WithMockUser(roles = "USER")
    public void testGetNearbyInstitutions()throws Exception{

        Institution institution = new Institution();
        institution.setId(4L);
        institution.setName("integrationTestCase");
        Location testLocation = new Location();
        testLocation.setName("testLoc");
        testLocation.setCountry("Hungary");
        testLocation.setStreet("Kálmánháza");
        testLocation.setLat(47.8833298);
        testLocation.setLon(21.583331);
        institution.setLocation(testLocation);

        MvcResult mvcResult = mockMvc.perform(post("/institutions").with(csrf())
                        .content(objectMapper.writeValueAsString(institution))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andDo(print());

        MvcResult mvcResult2 = mockMvc.perform(get("/institutions/nearby").param("userId","1").param("maxDistance","20").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult2))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(responseBody);

        int count = jsonNode.size();

        assertEquals(1, count);

    }
}
