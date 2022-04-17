package com.example.sportmate.repository.activity;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.enumeration.GenreEnum;
import com.example.sportmate.repository.searchCriteria.SearchCriteria;
import com.example.sportmate.repository.searchCriteria.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.example.sportmate.repository.searchCriteria.SearchOperation.*;
import static java.lang.Boolean.*;
import static java.util.Arrays.stream;

public class ActivitySpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ActivitySpecificationsBuilder() {
        params = new ArrayList<>();
    }

    private static Object instantiateObjectType(final String value) {
        if (isBoolean(value)) {
            return parseBoolean(value);
        }
        if (stream(GenreEnum.values()).anyMatch(genreEnum -> genreEnum.name().equals(value))) {
            return GenreEnum.valueOf(value);
        }
        return value;
    }

    private static boolean isBoolean(final String value) {
        return TRUE.toString().equals(value) || FALSE.toString().equals(value);
    }

    public void with(final String key,
                     final String operation,
                     final String value,
                     final String prefix,
                     final String suffix) {
        SearchOperation searchOperation = retrieveSearchOperation(operation.charAt(0));
        if (searchOperation != null) {
            if (EQUALITY == searchOperation) {
                final boolean startWithAsterisk = prefix.contains("*");
                final boolean endWithAsterisk = suffix.contains("*");
                if (startWithAsterisk && endWithAsterisk) {
                    searchOperation = CONTAINS;
                } else if (startWithAsterisk) {
                    searchOperation = ENDS_WITH;
                } else if (endWithAsterisk) {
                    searchOperation = STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(key, searchOperation, instantiateObjectType(value)));
        }
    }

    public Specification<Activity> build() {
        if (params.isEmpty()) {
            return null;
        }
        Specification<Activity> result = new ActivitySpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification
                    .where(result)
                    .and(new ActivitySpecification(params.get(i)));
        }
        return result;
    }
}
