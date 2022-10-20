package edu.miu.dto

import javax.validation.constraints.NotBlank

data class SignInRequest(
    @field: NotBlank(message = "Username is required")
    val email: String,

    @field: NotBlank(message = "Password cannot be empty")
    val password: String
)