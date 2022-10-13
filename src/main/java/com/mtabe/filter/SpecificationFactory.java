package com.mtabe.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationFactory<T> {

  private Map<Filter, Function<SearchCriteria, Specification<T>>> specs;

  @PostConstruct
  private void init() {
    specs = new HashMap<>();
    specs.put(Filter.EQUAL, this::getEqualsSpecification);
    specs.put(Filter.GREATER_THAN, this::getGreaterThanSpecification);
    specs.put(Filter.LESS_THAN, this::getLessThanSpecification);
  }

  public Specification<T> getByCriteria(SearchCriteria criteria) {
    return specs.get(criteria.getFilter()).apply(criteria);
  }

  private Specification<T> getEqualsSpecification(SearchCriteria criteria) {
    return (root, query, builder) -> {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getVal() + "%");
      } else {
        return builder.equal(root.get(criteria.getKey()), criteria.getVal());
      }
    };
  }

  private Specification<T> getGreaterThanSpecification(SearchCriteria criteria) {
    return (root, query, builder) -> {
      return builder.greaterThan(root.<String>get(criteria.getKey()), criteria.getVal().toString());
    };
  }

  private Specification<T> getLessThanSpecification(SearchCriteria criteria) {
    return (root, query, builder) -> {
      return builder.lessThan(root.<String>get(criteria.getKey()), criteria.getVal().toString());
    };
  }
}
