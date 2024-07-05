package com.example.demo.dao;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class SelectSqlRequest {
    private final List<Object> values;
    private final String sql;

    private SelectSqlRequest(List<Object> values, String sql) {
        this.values = values;
        this.sql = sql;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> columns = new ArrayList<>();
        private List<String> conditions;
        private List<String> joinTables;
        private String mainTable;
        private List<String> modifiers;
        private List<Object> values;

        public Builder addColumn(String column) {
            columns.add(column);
            return this;
        }

        public Builder columns(List<String> columns) {
            this.columns = columns;
            return this;
        }

        public Builder conditions(List<String> conditions) {
            this.conditions = conditions;
            return this;
        }

        public Builder joinTables(List<String> joinTables) {
            this.joinTables = joinTables;
            return this;
        }

        public Builder mainTable(String mainTable) {
            this.mainTable = mainTable;
            return this;
        }

        public Builder modifiers(List<String> modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public Builder values(List<Object> values) {
            this.values = values;
            return this;
        }

        private String buildSelectSqlRequest() {
            if (columns == null || columns.isEmpty() || mainTable == null || mainTable.isEmpty()) {
                return "";
            }

            StringBuilder request = new StringBuilder("SELECT ");

            request.append(String.join(", ", columns));

            request.append(" FROM ");

            request.append(mainTable);

            if (joinTables != null && !joinTables.isEmpty()) {
                for (String joinTable : joinTables) {
                    request.append(" JOIN ").append(joinTable);
                }
            }

            if (conditions != null && !conditions.isEmpty()) {
                request.append(" WHERE ");
                String condition = String.join(" AND ", conditions);
                request.append(condition);
            }

            if (modifiers != null && !modifiers.isEmpty()) {
                request.append(" ");
                String modifier = String.join(" ", modifiers);
                request.append(modifier);
            }
            return request.toString();
        }

        public SelectSqlRequest build() {
            String sql = buildSelectSqlRequest();
            return new SelectSqlRequest(values, sql);
        }
    }
}
