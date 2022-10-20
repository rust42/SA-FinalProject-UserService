package com.edu.miu.security

import com.edu.miu.AUTHORITIES
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date

@Component
class TokenGenerator {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.issuer}")
    private lateinit var jwtIssuer: String


    fun generateAccessToken(authentication: Authentication): String {
        val signingKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())
        return Jwts.builder()
            .setIssuer(jwtIssuer)
            .setSubject(authentication.name)
            .claim(AUTHORITIES, authentication.authorities)
            .setIssuedAt(Date())
            .setExpiration(Date.from(Instant.now().plusSeconds(36000)))
            .signWith(signingKey)
            .compact()
    }

}