package edu.miu.service

import edu.miu.dto.*
import edu.miu.entity.User
import edu.miu.exception.InvalidCredentialError
import edu.miu.exception.UserAlreadyExistsException
import edu.miu.repo.UserRepo
import edu.miu.security.SignedInUser
import edu.miu.security.TokenGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenGenerator: TokenGenerator

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
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

    override fun loginUser(request: SignInRequest): SignInResponse {
        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
            )
            val principal = authentication.principal as? SignedInUser
            val token = tokenGenerator.generateAccessToken(authentication)

            if (principal == null) {
                throw InvalidCredentialError("Invalid authentication!")
            }
            val user = userRepo.findByEmail(principal.username) ?: throw InvalidCredentialError("Invalid authentication!")

            return SignInResponse(token, user.email, user.firstName, user.lastName)
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