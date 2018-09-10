package com.code_hq.core.ui.api;

import com.code_hq.core.domain.application.Application;
import com.code_hq.core.domain.application.ApplicationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void givenApplications_whenListApplications_thenExpectedResource()
    throws Exception
    {
        Application jmc = insert("jmc", "Jupiter Mining Corp.");
        Application sb = insert("sb", "Sample Broker");

        mockMvc
            .perform(get("/api/applications")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id", is(jmc.getId())))
            .andExpect(jsonPath("$[0].name", is(jmc.getName())))
            .andExpect(jsonPath("$[1].id", is(sb.getId())))
            .andExpect(jsonPath("$[1].name", is(sb.getName())));
    }

    @Test
    public void givenApplications_whenGetOneApplication_thenExpectedResource()
    throws Exception {

        Application jmc = insert("jmc", "Jupiter Mining Corp.");

        mockMvc
            .perform(get("/api/applications/jmc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(jmc.getId())))
            .andExpect(jsonPath("$.name", is(jmc.getName())));
    }

    private Application insert(String id, String name)
    {
        Application application = Application.register(id, name);

        return applicationRepository.save(application);
    }
}
