package com.testingpractice.duoclonebackend.commons.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

  private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

  @ExceptionHandler(ApiException::class)
  fun handleApi(ex: ApiException): ResponseEntity<ProblemDetail> {
    val pd = ProblemDetail.forStatus(ex.status())
    pd.title = ex.code.name
    pd.detail = ex.message
    pd.setProperty("code", ex.code.name)

    return ResponseEntity.status(ex.status()).body(pd)
  }

  @ExceptionHandler(Exception::class)
  fun handleAny(ex: Exception, req: HttpServletRequest): ResponseEntity<ProblemDetail> {
    val pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    log.error("Unhandled {} {}", req.method, req.requestURI, ex)

    pd.title = "INTERNAL_ERROR"
    pd.detail = "Unexpected error"

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd)
  }
}