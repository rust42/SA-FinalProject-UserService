package edu.miu.controller

import edu.miu.dto.*
import edu.miu.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
class AuthController {
    @Autowired
    private lateinit var userService: UserService
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@RequestBody @Valid request: SignUpRequest): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.register(request))
    }

    @PostMapping("/signin")
    fun signin(@RequestBody @Valid request: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity.ok(userService.loginUser(request))
    }

    @PostMapping("/verify")
    fun verify(): ResponseEntity<TokenValidResponse> {
        return ResponseEntity.ok(userService.verify())
    }
}