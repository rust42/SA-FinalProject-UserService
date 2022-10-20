package edu.miu.exception

data class InvalidCredentialError(override val message: String): Exception(message) {}