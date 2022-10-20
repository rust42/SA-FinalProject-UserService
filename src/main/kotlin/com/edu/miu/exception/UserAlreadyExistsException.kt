package com.edu.miu.exception

data class UserAlreadyExistsException(override val message: String): Exception(message) {}