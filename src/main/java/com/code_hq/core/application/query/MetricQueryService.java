package com.code_hq.core.application.query;

import com.code_hq.core.application.dto.metric.FractionalMetricDto;
import com.code_hq.core.application.dto.metric.MetricDto;
import com.code_hq.core.application.dto.metric.SimpleMetricDto;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MetricQueryService
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<MetricDto> findAll()
    {
        // TODO: Might be simpler and better to just use JPA to manage the read models via a read-only repo?
        String query =
            "SELECT *, " +
            "CASE " +
                "WHEN metric_fraction.id IS NOT NULL THEN 'f' " +
                "WHEN metric_simple.id IS NOT NULL THEN 's' " +
            "END clazz " +
            "FROM metric " +
            "LEFT JOIN metric_fraction ON metric_fraction.id = metric.id " +
            "LEFT JOIN metric_simple ON metric_simple.id = metric.id";

        return jdbcTemplate.query(query, new MetricRowMapper());
    }

    public Optional<MetricDto> findById(String id)
    {
        String query =
            "SELECT *, " +
            "CASE " +
                "WHEN metric_fraction.id IS NOT NULL THEN 'f' " +
                "WHEN metric_simple.id IS NOT NULL THEN 's' " +
            "END clazz " +
            "FROM metric " +
            "LEFT JOIN metric_fraction ON metric_fraction.id = metric.id " +
            "LEFT JOIN metric_simple ON metric_simple.id = metric.id " +
            "WHERE metric.id = :id";

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, namedParameters, new MetricRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Boolean existsById(String id)
    {
        String query = "SELECT EXISTS(SELECT 1 FROM metric WHERE id = :id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        return jdbcTemplate.queryForObject(query, namedParameters, Boolean.class);
    }

    private static class MetricRowMapper implements RowMapper<MetricDto>
    {
        @Override
        public MetricDto mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            switch (rs.getString("clazz")) {
                case "s":
                    return new SimpleMetricDto(rs.getString("id"), rs.getString("name"));
                case "f":
                    return new FractionalMetricDto(rs.getString("id"), rs.getString("name"));
            }

            throw new SQLException("Cannot map metric row.");
        }
    }
}
