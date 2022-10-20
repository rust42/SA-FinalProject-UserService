package edu.miu.dto

data class TokenValidResponse(val valid: Boolean = true,
                              val email: String,
                              val firstName: String,
                              val lastName: String)