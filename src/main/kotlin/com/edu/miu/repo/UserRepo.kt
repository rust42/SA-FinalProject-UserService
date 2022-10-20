package com.edu.miu.repo

import com.edu.miu.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<User, String> {
    fun findByEmail(email: String): User?
}