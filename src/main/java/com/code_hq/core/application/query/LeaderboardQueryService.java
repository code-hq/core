package com.code_hq.core.application.query;

import com.code_hq.core.application.query.model.leaderboard.Score;
import com.code_hq.core.domain.metric.Metric;
import lombok.AllArgsConstructor;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class LeaderboardQueryService
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Score> findAll()
    {
        String query =
                "SELECT n.*, m.direction, a.name application_name FROM " +
                "(SELECT DISTINCT ON (a.id, s.version_id, s.metric_id) a.id application_id, s.version_id, s.metric_id, s.collected_at, a.name, " +
                "    CASE " +
                "WHEN denominator IS NOT NULL AND denominator <> '0' THEN (numerator/denominator * 100) " +
                "ELSE value " +
                "END use_value " +
                "FROM application a " +
                "LEFT JOIN score s ON a.id = s.application_id " +
                "LEFT JOIN value v ON v.score_id = s.id " +
                "GROUP BY a.id, s.version_id, s.metric_id, s.collected_at, v.numerator, v.denominator, v.value, s.id " +
                "HAVING s.version_id = 'master' OR s.id IS NULL " + // TODO: Allow different default version to use
                "ORDER BY a.id, s.version_id, s.metric_id, s.collected_at DESC) n " +
                "LEFT JOIN metric m ON m.id = n.metric_id " +
                "LEFT JOIN application a ON a.id = n.application_id " +
                "WHERE m.contribution_policy = 1 OR m.id IS NULL;";


        /*
        SELECT DISTINCT ON (a.id, s.version_id, s.metric_id) a.id application_id, s.version_id, s.metric_id, s.collected_at, a.name
	FROM application a
        LEFT JOIN score s ON a.id = s.application_id
	GROUP BY a.id, s.version_id, s.metric_id, s.collected_at, s.id
	HAVING s.version_id = 'master' OR s.id IS NULL
	ORDER BY a.id, s.version_id, s.metric_id, s.collected_at DESC;
         */

        return jdbcTemplate.query(query, new LeaderboardQueryService.LeaderboardScoreRowMapper());
    }

    private static class LeaderboardScoreRowMapper implements RowMapper<Score>
    {
        @Override
        public Score mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return new Score(
                    rs.getString("application_id"),
                    rs.getString("application_name"),
                    rs.getString("metric_id"),
                    rs.getDouble("use_value"),
                    Metric.Direction.HIGHER_IS_BEST // TODO: No clue how to convert the DB value to one of Direction
            );
        }
    }
}
