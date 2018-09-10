package com.code_hq.core.application.query;

import com.code_hq.core.application.dto.application.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationQueryService
{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<ApplicationDto> findAll()
    {
        String query = "SELECT * FROM application";

        return jdbcTemplate.query(query, new ApplicationRowMapper());
    }

    public Optional<ApplicationDto> findById(String id)
    {
        String query = "SELECT * FROM application WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, namedParameters, new ApplicationRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Boolean existsById(String id)
    {
        String query = "SELECT EXISTS(SELECT FROM application WHERE id = :id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        return jdbcTemplate.queryForObject(query, namedParameters, Boolean.class);
    }

    private static class ApplicationRowMapper implements RowMapper<ApplicationDto>
    {
        @Override
        public ApplicationDto mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return new ApplicationDto(rs.getString("id"), rs.getString("name"));
        }
    }
}
