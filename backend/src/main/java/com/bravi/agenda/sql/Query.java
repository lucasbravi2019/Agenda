package com.bravi.agenda.sql;

import com.bravi.agenda.constant.QueryType;
import com.bravi.agenda.sql.executor.NativeQueryExecutor;
import lombok.Getter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Getter
public class Query<T, S> {
    private final String query;
    private final Map<String, Object> params = new HashMap<>();
    private Map<String, ?>[] batchParams;
    private RowMapper<? extends T> rowMapper;
    private ResultSetExtractor<? extends T> resultSetExtractor;
    private final QueryType queryType;
    private final NativeQueryExecutor queryExecutor;
    private S result = null;

    public Query(String query, QueryType queryType, NativeQueryExecutor queryExecutor) {
        this.query = query;
        this.queryType = queryType;
        this.queryExecutor = queryExecutor;
    }

    private void setRowMapper(RowMapper<? extends T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    private void setResultSetExtractor(ResultSetExtractor<? extends T> resultSetExtractor) {
        this.resultSetExtractor = resultSetExtractor;
    }

    private void setBatchParams(Map<String, ?>[] batchParams) {
        this.batchParams = batchParams;
    }

    public static <T, S> Query<T, S> newQuery(String name, QueryType queryType, NativeQueryExecutor nativeQueryExecutor) {
        return QueryBuilder.query(name, queryType, nativeQueryExecutor);
    }

    public Query<T, S> param(String paramName, Object value) {
        return ParamsBuilder.param(this, paramName, value);
    }

    public Query<T, S> rowMapper(RowMapper<? extends T> rowMapper) {
        return RowMapperBuilder.rowMapper(this, rowMapper);
    }

    public Query<T, S> resultSetExtractor(ResultSetExtractor<? extends T> resultSetExtractor) {
        return ResultSetExtractorBuilder.resultSetExtractor(this, resultSetExtractor);
    }

    public Query<T, S> batchParams(Map<String, ?>[] batchParams) {
        return BatchParamsBuilder.batchParams(this, batchParams);
    }

    public boolean hasParams() {
        return !params.isEmpty();
    }

    public S getResult() {
        if (QueryType.SELECT.equals(queryType)) {
            if (getRowMapper() != null)
                result = (S) queryExecutor.query(this);
            if (getResultSetExtractor() != null)
                result = (S) queryExecutor.queryForObject(this);
        }

        return result;
    }

    public void execute() {
        if (QueryType.MODIFICATION.equals(this.queryType))
            queryExecutor.executeQuery(this);
    }

    public void batchUpdate() {
        if (QueryType.MODIFICATION.equals(this.queryType))
            queryExecutor.batchUpdate(this);
    }

    private static class QueryBuilder {

        public static <T, S> Query<T, S> query(String name, QueryType queryType,
                                               NativeQueryExecutor nativeQueryExecutor) {
            Properties queries = nativeQueryExecutor.getQueries();
            String query = (String) queries.get(name);

            if (query == null)
                throw new IllegalArgumentException("Query not found: " + name);

            return new Query<>(query, queryType, nativeQueryExecutor);
        }

    }

    private static class ParamsBuilder {
        public static <T, S> Query<T, S> param(Query<T, S> query, String paramName, Object value) {
            if (paramName != null && value != null)
                query.params.put(paramName, value);

            return query;
        }

    }

    private static class RowMapperBuilder {
        public static <T, S> Query<T, S> rowMapper(Query<T, S> query, RowMapper<? extends T> rowMapper) {
            if (query != null && rowMapper != null)
                query.setRowMapper(rowMapper);

            return query;
        }
    }

    private static class ResultSetExtractorBuilder {
        public static <T, S> Query<T, S> resultSetExtractor(Query<T, S> query, ResultSetExtractor<? extends T> resultSetExtractor) {
            if (query != null && resultSetExtractor != null)
                query.setResultSetExtractor(resultSetExtractor);

            return query;
        }

    }

    private static class BatchParamsBuilder {
        public static <T, S> Query<T, S> batchParams(Query<T, S> query, Map<String, ?>[] batchParams) {
            if (query != null && batchParams != null)
                query.setBatchParams(batchParams);

            return query;
        }
    }

}
