package com.code_hq.core.application.query;

import com.code_hq.core.application.query.model.score.ScoreDetails;
import com.code_hq.core.application.query.model.score.value.FractionalValue;
import com.code_hq.core.application.query.model.score.value.SimpleValue;
import com.code_hq.core.application.query.model.score.value.Value;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class ScoreQueryService
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<ScoreDetails> findAllByApplicationId(String id)
    {
        String query = "SELECT s.id, s.collected_at, s.scope AS scope, s.version_id, v.type, m.name AS metric_name," +
                " v.value, v.numerator, v.denominator FROM score AS s " +
                "LEFT JOIN value AS v ON v.score_id = s.id " +
                "LEFT JOIN metric AS m ON s.metric_id = m.id " +
                "WHERE s.application_id = :id " +
                "ORDER BY s.collected_at DESC " +
                "LIMIT 100";

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        return jdbcTemplate.query(query, namedParameters, new ScoreDetailsRowMapper());
    }


    private static class ScoreDetailsRowMapper implements RowMapper<ScoreDetails>
    {
        @Override
        public ScoreDetails mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            int valueType = rs.getInt("type");

            Value value;

            if (valueType == 1) {
                value = new FractionalValue(rs.getDouble("numerator"), rs.getDouble("denominator"));
            } else if (valueType == 2) {
                value = new SimpleValue(rs.getDouble("value"));
            } else {
                throw new SQLException("Cannot map value.");
            }

            rs.getInt("scope");

            String scope = rs.wasNull() ? "application" : "Unknown";

            return new ScoreDetails(
                rs.getString("metric_name"),
                scope,
                rs.getString("version_id"),
                value,
                new Date(rs.getTimestamp("collected_at").getTime())
            );
        }
    }
}
