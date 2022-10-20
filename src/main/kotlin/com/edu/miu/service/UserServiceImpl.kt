package com.edu.miu.service

import com.edu.miu.dto.*
import com.edu.miu.entity.User
import com.edu.miu.exception.InvalidCredentialError
import com.edu.miu.exception.UserAlreadyExistsException
import com.edu.miu.repo.UserRepo
import com.edu.miu.security.SignedInUser
import com.edu.miu.security.TokenGenerator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepo: UserRepo,
                      val authenticationManager: AuthenticationManager,
                      val tokenGenerator: TokenGenerator,
                      val passwordEncoder: PasswordEncoder): UserService {

    override fun register(request: SignUpRequest): UserDto {
        if (userRepo.findByEmail(request.email!!) != null) {
            throw UserAlreadyExistsException("The email is already registered!")
        }
        val user = userRepo.save(
            User().apply {
                email = request.email!!
                firstName = request.firstName!!
                lastName = request.lastName!!
                password = passwordEncoder.encode(request.password!!)
            }
        )
        return UserDto (
            user.email,
            user.firstName,
            user.lastName
        )
    }

    override fun loginUser(request: SignInRequest): TokenDto {
        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
            )
            val user = authentication.principal as? SignedInUser
            val token = tokenGenerator.generateAccessToken(authentication)
            if (user == null) {
                throw InvalidCredentialError("Invalid authentication!")
            }
            return TokenDto(token, user.username)
        } catch (ex: BadCredentialsException) {
            throw InvalidCredentialError("Invalid credential passed!")
        }
    }

    override fun verify(): TokenValidResponse {
       val email = SecurityContextHolder.getContext().authentication.principal as? String
           ?: throw InvalidCredentialError("User not found!")

        val user = userRepo.findByEmail(email) ?: throw UsernameNotFoundException("No such user exists!")

        return TokenValidResponse(true, user.email, user.firstName, user.lastName)
    }
}