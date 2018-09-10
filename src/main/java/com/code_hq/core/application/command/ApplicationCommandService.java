package com.code_hq.core.application.command;

import com.code_hq.core.domain.application.Application;
import com.code_hq.core.domain.application.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationCommandService
{
    private ApplicationRepository applicationRepository;

    public void register(final String id, final String name)
    {
        Application application = Application.register(id, name);

        applicationRepository.save(application);
    }

    public void rename(final String id, final String name) throws IllegalArgumentException
    {
        Application application = applicationRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cannot find application with ID " + id + "."));

        application.rename(name);

        applicationRepository.save(application);
    }


    public void delete(final String id) throws IllegalArgumentException
    {
        try {
            applicationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Cannot find application with ID " + id + ".", e);
        }
    }
}
