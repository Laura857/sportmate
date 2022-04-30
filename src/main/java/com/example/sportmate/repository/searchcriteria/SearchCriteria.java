package com.example.sportmate.repository.searchcriteria;

public record SearchCriteria(
        String key,
        SearchOperation operation,
        Object value) {

}
