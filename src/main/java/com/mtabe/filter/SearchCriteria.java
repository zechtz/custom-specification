package com.mtabe.filter;

import lombok.Data;

@Data
public class SearchCriteria {
  private String key;

  private Filter filter;
  private Object val;
  private String predicate;

  public SearchCriteria(String key, String filter, Object val, String predicate) {
    this.key = key;
    this.filter = Filter.filterFromVal(filter);
    this.val = val;
    this.predicate = predicate;
  }

  public boolean isOrPredicate() {
    return predicate.equalsIgnoreCase("OR");
  }
}
