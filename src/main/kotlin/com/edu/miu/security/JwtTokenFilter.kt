package com.edu.miu.security

import com.edu.miu.*
import com.edu.miu.dto.ApiError
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtTokenFilter: OncePerRequestFilter() {

    @Value("\${jwt.prefix}")
    private lateinit var jwtPrefix: String

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY)
        if (authorizationHeader?.startsWith(BEARER_TOKEN_PREFIX) != true) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.substring(BEARER_TOKEN_PREFIX.length)

        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.toByteArray()))
                .build()
                .parseClaimsJws(token)
            val username = claims.body.subject
            val authorities = claims.body.get(AUTHORITIES, ArrayList::class.java)
                .mapNotNull { (it as Map<*, *>)[AUTHORITY].let { it1 -> it1 as String } }
                .map { SimpleGrantedAuthority(it) }

            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
            )
            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            val mapper = ObjectMapper() 
            val writer = response.writer
            val error = ApiError(HttpStatus.UNAUTHORIZED, "Invalid token error!", exception)
            writer.write(mapper.writeValueAsString(error))
            writer.flush()
        }
    }
}
