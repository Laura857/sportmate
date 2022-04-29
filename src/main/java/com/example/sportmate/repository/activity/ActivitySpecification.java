package com.example.sportmate.repository.activity;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.repository.searchCriteria.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@AllArgsConstructor
public class ActivitySpecification implements Specification<Activity> {

    private static final List<String> ACTIVITY_FIELDS_NAMES = asList(
            "isEvent", "activityName", "activityDate", "address", "participant", "contact");

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(final Root<Activity> activityRoot,
                                 @NonNull final CriteriaQuery<?> query,
                                 @NonNull final CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicate = new ArrayList<>();
//        final Root<Users> userRoot = query.from(Users.class);
        final Join<Users, Activity> userActivityJoin = activityRoot.join("creator");
        final Join<Activity, Sport> activitySportJoin = activityRoot.join("sport");
        final Join<Activity, Level> activityLevelJoin = activityRoot.join("activityLevel");

        if (ACTIVITY_FIELDS_NAMES.contains(criteria.key())) {
            switch (criteria.operation()) {
                case EQUALITY -> predicate.add(criteriaBuilder.equal(activityRoot.get(criteria.key()), criteria.value()));
                case NEGATION -> criteriaBuilder.notEqual(activityRoot.get(criteria.key()), criteria.value());
                case GREATER_THAN -> criteriaBuilder.greaterThan(activityRoot.get(criteria.key()), criteria.value().toString());
                case LESS_THAN -> criteriaBuilder.lessThan(activityRoot.get(criteria.key()), criteria.value().toString());
                case LIKE -> criteriaBuilder.like(activityRoot.get(criteria.key()), criteria.value().toString());
                case STARTS_WITH -> criteriaBuilder.like(activityRoot.get(criteria.key()), criteria.value() + "%");
                case ENDS_WITH -> criteriaBuilder.like(activityRoot.get(criteria.key()), "%" + criteria.value());
                case CONTAINS -> criteriaBuilder.like(activityRoot.get(criteria.key()), "%" + criteria.value() + "%");
            }
        } else if ("activityLevel".equals(criteria.key())) {
            predicate.add(criteriaBuilder.equal(activityLevelJoin.get("label"), criteria.value()));
        } else if ("sport".equals(criteria.key())) {
            predicate.add(criteriaBuilder.equal(activitySportJoin.get("label"), criteria.value()));
        } else {
            predicate.add(criteriaBuilder.equal(userActivityJoin.get(criteria.key()), criteria.value()));
        }
        return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
    }
}

