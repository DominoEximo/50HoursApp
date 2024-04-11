package hu.inf.unideb.thesis.integration.institution;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.service.InstitutionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
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
public class InstitutionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InstitutionService institutionService;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetInstitutions() throws Exception {

        institutionService.setUpMockedData();


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
    @Transactional
    public void testCreateInstitutionsThenDelete() throws Exception{

        institutionService.setUpMockedData();

        Institution institution = new Institution();
        institution.setId(2L);
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
}
