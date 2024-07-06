package com.example.demo.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//ЗАДАТЬ ВОПРОС В КАКУЮ ЛУЧШЕ ПАПКУ ТАКОЕ КЛАСТЬ BUILDER?

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
//  МЕТОДЫ ЛУЧШЕ ВО МНОЖЕСТВЕННОМ ЧИСЛЕ НАЗЫВАТЬ В ТАКОМ СЛУЧАЕ ИЛИ В ЕДИНСТВЕННОМ
    public void columns(String... column) {
        columns.addAll(List.of(column));
    }

    public void conditions(String... condition) {
        conditions.addAll(List.of(condition));
    }

    public void joinTables(String... joinTables) {
        this.joinTables.addAll(List.of(joinTables));
    }

    public void mainTable(String mainTable) {
        this.mainTable = mainTable;
    }

    public void modifiers(String... modifiers) {
        this.modifiers.addAll(List.of(modifiers));
    }

    public void values(Object... values) {
        this.values.addAll(List.of(values));
    }

    private void validateParameters() {
        if (columns.isEmpty() || mainTable == null) {
            throw new RuntimeException("You must specify at least one column and mainTable");
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
