package com.edu.miu.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SignUpRequest(
    @field:Email(message = "Not valid")
        var email: String?,
    @field:NotBlank(message = "Not valid")
        var firstName: String?,
    @field:NotBlank(message = "Not valid")
        var lastName: String?,
    @field:Size(message = "Not valid: must be >= 8 and <= 20 characters", min = 8, max = 20)
        var password: String?)

