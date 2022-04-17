package com.example.sportmate.repository.searchCriteria;

public record SearchCriteria(
        String key,
        SearchOperation operation,
        Object value) {

}
