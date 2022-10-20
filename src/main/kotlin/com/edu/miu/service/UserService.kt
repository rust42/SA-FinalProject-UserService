package com.edu.miu.service

import com.edu.miu.dto.*

interface UserService {
    fun register(request: SignUpRequest): UserDto
    fun loginUser(request: SignInRequest): TokenDto
    fun verify(): TokenValidResponse
}