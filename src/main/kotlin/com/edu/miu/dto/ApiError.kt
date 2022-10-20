package com.edu.miu.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {

    @JsonIgnore
    var httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
    var message: String? = null
    val status = "Error"

    val statusCode : Int
        get() = httpStatus.value()

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    var errors: Map<String, String> = HashMap()

    constructor(status: HttpStatus) {
        this.httpStatus = status
    }

    constructor(httpStatus: HttpStatus, throwable: Throwable): this(httpStatus) {
        this.message = "Unexpected error"
    }

    constructor(httpStatus: HttpStatus, errors: Map<String, String>, throwable: Throwable): this(httpStatus, throwable) {
        this.errors = errors
    }

    constructor(httpStatus: HttpStatus, message: String, throwable: Throwable): this(httpStatus, throwable) {
        this.message = message
    }
}