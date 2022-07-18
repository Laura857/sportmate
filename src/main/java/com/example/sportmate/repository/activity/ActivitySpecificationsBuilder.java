package com.example.sportmate.repository.activity;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.repository.searchcriteria.SearchCriteria;
import com.example.sportmate.repository.searchcriteria.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.example.sportmate.InstanceUtils.instantiateObjectType;
import static com.example.sportmate.repository.searchcriteria.SearchOperation.*;

public class ActivitySpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ActivitySpecificationsBuilder() {
        params = new ArrayList<>();
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
