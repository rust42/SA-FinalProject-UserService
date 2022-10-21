package edu.miu.dto

data class SignInResponse(val token: String,
                          val email: String,
                          val firstName: String,
                          val lastName: String)