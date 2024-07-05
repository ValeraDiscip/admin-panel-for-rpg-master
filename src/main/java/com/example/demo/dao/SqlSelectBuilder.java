package com.example.demo.dao;

import liquibase.pro.packaged.S;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class SqlSelectBuilder {
    private final List<String> columns;
    private final List<String> conditions;
    private final List<String> joinTables;
    private String mainTable;
    private final List<String> modifiers;
    private final List<Object> values;

    public SqlSelectBuilder(List<String> columns, List<String> conditions, List<String> joinTables, String mainTable, List<String> modifiers, List<Object> values) {
        this.columns = columns;
        this.conditions = conditions;
        this.joinTables = joinTables;
        this.mainTable = mainTable;
        this.modifiers = modifiers;
        this.values = values;
    }

    public SqlSelectBuilder() {
        this.columns = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.joinTables = new ArrayList<>();
        this.modifiers = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public SqlSelectBuilderResult build() {
        String sql = buildSelectSqlRequest();
        return new SqlSelectBuilderResult(values, sql);
    }

    private String buildSelectSqlRequest() {
        validateParameters();

        StringBuilder request = new StringBuilder("SELECT ");

        request.append(String.join(", ", columns));
        request.append(" FROM ");
        request.append(mainTable);

        for (String joinTable : joinTables) {
            request.append(" JOIN ").append(joinTable);
        }

        if (!conditions.isEmpty()) {
            request.append(" WHERE ");
            String condition = String.join(" AND ", conditions);
            request.append(condition);
        }

        if (!modifiers.isEmpty()) {
            request.append(" ");
            String modifier = String.join(" ", modifiers);
            request.append(modifier);
        }

        return request.toString();
    }

    public SqlSelectBuilder column(String ...column) {
        columns.addAll(List.of(column));
        return this;
    }

    public SqlSelectBuilder condition(String ...condition) {
        conditions.addAll(List.of(condition));
        return this;
    }

    private void validateParameters() {
        if (columns.isEmpty() || mainTable == null) {
            throw new RuntimeException("You must specify at least one column or mainTable");
        }
    }

    @RequiredArgsConstructor
    public static class SqlSelectBuilderResult {
        private final List<Object> values;
        @Getter
        private final String sql;

        public Object[] getValues() {
            return values.toArray();
        }
    }
}
