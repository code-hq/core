package com.code_hq.core.domain.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationRepositoryIntegrationTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void whenFindById_thenReturnApplication()
    {
        // Given
        Application jmc = Application.register("jmc", "Jupiter Mining Corp.");
        entityManager.persist(jmc);
        entityManager.flush();

        // When
        Optional<Application> found = applicationRepository.findById(jmc.getId());

        // Then
        assertThat(found.isPresent()).as("ApplicationDto is readable").isTrue();
        assertThat(found.get().getId()).as("ID is equal").isEqualTo(jmc.getId());
        assertThat(found.get().getName()).as("Name is equal").isEqualTo(jmc.getName());
    }
}
