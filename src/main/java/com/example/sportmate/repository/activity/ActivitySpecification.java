package com.example.sportmate.repository.activity;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.repository.searchcriteria.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.sportmate.InstanceUtils.isLocalDate;
import static java.util.Arrays.asList;

@AllArgsConstructor
public class ActivitySpecification implements Specification<Activity> {//TODO refactor and fixe join

    private static final List<String> ACTIVITY_FIELDS_NAMES = asList(
            "isEvent", "activityName", "activityDate", "address", "participant", "contact");

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(final Root<Activity> activityRoot,
                                 @NonNull final CriteriaQuery<?> query,
                                 @NonNull final CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicate = new ArrayList<>();
//        final Root<Users> userRoot = query.from(Users.class);

        if (ACTIVITY_FIELDS_NAMES.contains(criteria.key())) {
            switch (criteria.operation()) {
                case EQUALITY -> predicate.add(criteriaBuilder.equal(activityRoot.get(criteria.key()), criteria.value()));
                case NEGATION -> predicate.add(criteriaBuilder.notEqual(activityRoot.get(criteria.key()), criteria.value()));
                case GREATER_THAN -> predicate.add(criteriaBuilder.greaterThan(activityRoot.get(criteria.key()), criteria.value().toString()));
                case LESS_THAN -> predicate.add(criteriaBuilder.lessThan(activityRoot.get(criteria.key()), criteria.value().toString()));
                case LIKE -> predicate.add(criteriaBuilder.like(activityRoot.get(criteria.key()), criteria.value().toString()));
                case STARTS_WITH -> predicate.add(criteriaBuilder.like(activityRoot.get(criteria.key()), criteria.value() + "%"));
                case ENDS_WITH -> predicate.add(criteriaBuilder.like(activityRoot.get(criteria.key()), "%" + criteria.value()));
                case CONTAINS -> predicate.add(criteriaBuilder.like(activityRoot.get(criteria.key()), "%" + criteria.value() + "%"));
            }
        } else if ("activityLevel".equals(criteria.key())) {
            final Join<Activity, Level> activityLevelJoin = activityRoot.join("activityLevel");
            predicate.add(criteriaBuilder.equal(activityLevelJoin.get("label"), criteria.value()));
        } else if ("sport".equals(criteria.key())) {
            final Join<Activity, Sport> activitySportJoin = activityRoot.join("sport");
            predicate.add(criteriaBuilder.equal(activitySportJoin.get("label"), criteria.value()));
        } else {
            final Join<Users, Activity> userActivityJoin = activityRoot.join("creator");
            switch (criteria.operation()) {
                case EQUALITY -> predicate.add(criteriaBuilder.equal(userActivityJoin.get(criteria.key()), criteria.value()));
                case NEGATION -> predicate.add(criteriaBuilder.notEqual(userActivityJoin.get(criteria.key()), criteria.value()));
                case GREATER_THAN -> {
                    if (isLocalDate(criteria.value().toString())) {
                        predicate.add(criteriaBuilder.greaterThan(userActivityJoin.get(criteria.key()), (LocalDate) criteria.value()));
                    } else {
                        predicate.add(criteriaBuilder.greaterThan(userActivityJoin.get(criteria.key()), criteria.value().toString()));
                    }
                }
                case LESS_THAN -> {
                    if (isLocalDate(criteria.value().toString())) {
                        predicate.add(criteriaBuilder.lessThan(userActivityJoin.get(criteria.key()), (LocalDate) criteria.value()));
                    } else {
                        predicate.add(criteriaBuilder.lessThan(userActivityJoin.get(criteria.key()), criteria.value().toString()));
                    }
                }
                case LIKE -> predicate.add(criteriaBuilder.like(userActivityJoin.get(criteria.key()), criteria.value().toString()));
                case STARTS_WITH -> predicate.add(criteriaBuilder.like(userActivityJoin.get(criteria.key()), criteria.value() + "%"));
                case ENDS_WITH -> predicate.add(criteriaBuilder.like(userActivityJoin.get(criteria.key()), "%" + criteria.value()));
                case CONTAINS -> predicate.add(criteriaBuilder.like(userActivityJoin.get(criteria.key()), "%" + criteria.value() + "%"));
            }
        }
        return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
    }
}

