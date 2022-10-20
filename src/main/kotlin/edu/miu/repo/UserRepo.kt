package edu.miu.repo

import edu.miu.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<User, String> {
    fun findByEmail(email: String): User?
}