package com.testingpractice.duoclonebackend.commons.exception

import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import org.springframework.http.HttpStatus

class ApiException : RuntimeException {

  val code: ErrorCode

  constructor(code: ErrorCode) : super(code.defaultMessage()) {
    this.code = code
  }

  constructor(code: ErrorCode, overrideMessage: String) : super(overrideMessage) {
    this.code = code
  }

  fun status(): HttpStatus {
    return code.status()
  }
}