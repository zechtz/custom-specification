package com.mtabe.filter;

import com.mtabe.exceptions.NoImplementationException;
import java.util.stream.Stream;

public enum Filter {
  EQUAL("eq"),
  NOT_EQUAL("neg"),
  GREATER_THAN("gt"),
  GREATER_THAN_OR_EQUAL_TO("gte"),
  LESS_THAN("lt"),
  LESSTHAN_OR_EQUAL_TO("lte"),
  IN("in"),
  NOT_IN("nin"),
  BETWEEN("btn"),
  CONTAINS("like");

  private String value;

  private Filter(String value) {
    this.value = value;
  }

  public static Filter filterFromVal(String value) {
    return Stream.of(values())
        .filter(entry -> String.valueOf(entry.value).equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(NoImplementationException::new);
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
