package com.code_hq.core.application.command;

import com.code_hq.core.domain.application.Application;
import com.code_hq.core.domain.application.ApplicationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ApplicationCommandServiceIntegrationTest
{
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration
    {
        @MockBean
        ApplicationRepository applicationRepository;

        @Bean
        public ApplicationCommandService applicationCommandService() {
            return new ApplicationCommandService(applicationRepository);
        }
    }

    @Autowired
    private ApplicationCommandService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Before
    public void setUp()
    {
        // Given
        Optional<Application> jmc = Optional.of(Application.register("jmc", "Jupiter Mining Corp."));

        // When
        when(applicationRepository.findById(jmc.get().getId())).thenReturn(jmc);
    }

    @Test
    public void whenValidId_thenApplicationShouldBeFound()
    {
        // When
        String id = "jmc";
        Optional<Application> result = applicationRepository.findById(id);

        // Then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
    }
}
