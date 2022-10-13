package com.mtabe.filter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class RequestBuilder {
  private RequestBuilder() {}

  public static PageRequest getPageRequest(
      Integer pageSize, Integer pageNumber, String sortCriteria) {
    var sortFields =
        new LinkedHashSet<>(
            Arrays.asList(StringUtils.split(StringUtils.defaultIfEmpty(sortCriteria, ""), ",")));

    var sortOrders = sortFields.stream().map(RequestBuilder::getOrder).collect(Collectors.toList());

    var sort = sortOrders.isEmpty() ? null : Sort.by(sortOrders);

    if (sort != null) {
      return PageRequest.of(
          ObjectUtils.defaultIfNull(pageNumber, 1) - 1,
          ObjectUtils.defaultIfNull(pageSize, 20),
          sort);
    } else {
      return PageRequest.of(
          ObjectUtils.defaultIfNull(pageNumber, 1) - 1, ObjectUtils.defaultIfNull(pageSize, 20));
    }
  }

  public static Order getOrder(String value) {
    if (StringUtils.startsWith(value, "-")) {
      return new Order(Direction.DESC, StringUtils.substringAfter(value, "-"));
    } else if (StringUtils.startsWith(value, "+")) {
      return new Order(Direction.ASC, StringUtils.substringAfter(value, "+"));
    } else {
      return new Order(Direction.ASC, StringUtils.trim(value));
    }
  }
}
