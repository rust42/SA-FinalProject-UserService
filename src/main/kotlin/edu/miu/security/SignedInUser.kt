package edu.miu.security

import edu.miu.ROLE_PREFIX
import edu.miu.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SignedInUser(user: User) : UserDetails {

    private val username: String
    private val grantedAuthorities: MutableList<GrantedAuthority>
    private val password: String

    init {
        username = user.email
        password = user.password
        grantedAuthorities = mutableListOf(SimpleGrantedAuthority("$ROLE_PREFIX$user.role.name"))
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}