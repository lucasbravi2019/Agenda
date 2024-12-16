package com.bravi.agenda.sql.executor;

import com.bravi.agenda.sql.Query;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Slf4j
@Component
public class NativeQueryExecutor {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Getter
    private final Properties queries;

    @Autowired
    public NativeQueryExecutor(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                               @Qualifier("nativeQueries") Properties queries) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.queries = queries;
    }

    public <T, S> void executeQuery(Query<T, S> query) {
        if (query.hasParams()) {
            logQueryWithParams(query);
            namedParameterJdbcTemplate.update(query.getQuery(), query.getParams());
        }
    }

    public <T, S> void batchUpdate(Query<T, S> query) {
        logQueryWithBatchParams(query);
        namedParameterJdbcTemplate.batchUpdate(query.getQuery(), query.getBatchParams());
    }

    public <T, S> List<? extends T> query(Query<T, S> query) {
        if (query.hasParams()) {
            logQueryWithParams(query);
            return namedParameterJdbcTemplate.query(query.getQuery(), query.getParams(), query.getRowMapper());
        } else {
            logQueryWithoutParams(query);
            return namedParameterJdbcTemplate.query(query.getQuery(), query.getRowMapper());
        }
    }

    public <T, S> T queryForObject(Query<T, S> query) {
        if (query.hasParams()) {
            logQueryWithParams(query);
            return namedParameterJdbcTemplate.query(query.getQuery(), query.getParams(), query.getResultSetExtractor());
        } else {
            logQueryWithoutParams(query);
            return namedParameterJdbcTemplate.query(query.getQuery(), query.getResultSetExtractor());
        }
    }

    private <T, S> void logQueryWithParams(Query<T, S> query) {
        log.info("Executing query: {}\nParams: {}", query.getQuery(), query.getParams());
    }

    private <T, S> void logQueryWithoutParams(Query<T, S> query) {
        log.info("Executing query: {}", query.getQuery());
    }

    private <T, S> void logQueryWithBatchParams(Query<T, S> query) {
        log.info("Executing batch query: {}\nParams: {}", query.getQuery(), query.getBatchParams());
    }

}
