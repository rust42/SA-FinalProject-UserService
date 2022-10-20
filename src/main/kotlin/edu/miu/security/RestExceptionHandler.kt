package edu.miu.security

import edu.miu.dto.ApiError
import edu.miu.exception.InvalidCredentialError
import edu.miu.exception.UserAlreadyExistsException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    private fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<Any> {
        val error = ApiError(HttpStatus.NOT_FOUND).apply {
            message = ex.message
        }
        return buildResponseEntity(error)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    private fun handleUserExistsException(ex: UserAlreadyExistsException): ResponseEntity<Any> {
        val error = ApiError(HttpStatus.NOT_FOUND).apply {
            message = ex.message
        }
        return buildResponseEntity(error)
    }

    @ExceptionHandler(InvalidCredentialError::class)
    private fun handleInvalidCredentialException(ex: InvalidCredentialError): ResponseEntity<Any> {
        val error = ApiError(HttpStatus.NOT_FOUND).apply {
            message = ex.message
        }
        return buildResponseEntity(error)
    }


    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = HashMap<String, String>()
        ex.bindingResult.allErrors. forEach {
            val field = (it as? FieldError)?.field
            val message = it.defaultMessage

            if (message != null && field != null) {
                errors[field] = message
            }
        }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, errors, ex)
        return ResponseEntity(apiError, apiError.httpStatus)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return buildResponseEntity(ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", ex))
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity(apiError, apiError.httpStatus)
    }
}