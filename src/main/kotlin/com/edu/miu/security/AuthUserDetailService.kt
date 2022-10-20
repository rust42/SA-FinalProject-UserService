package com.edu.miu.security

import com.edu.miu.repo.UserRepo
import com.edu.miu.security.SignedInUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthUserDetailService: UserDetailsService {


    @Autowired
    private lateinit var userRepo: UserRepo

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("No such user found")
        }

        val user = userRepo.findByEmail(username) ?: throw UsernameNotFoundException("Username not found $username")
        return SignedInUser(user)
    }

}