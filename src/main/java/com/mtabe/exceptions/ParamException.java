package com.mtabe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.parameter")
public class ParamException extends RuntimeException {
  private static final long serialVersionUID = 1L;
}
