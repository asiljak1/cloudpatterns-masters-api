package com.unsa.etf.cloudpatternsmastersapi.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.User as SecurityUser
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(val userClient: UserClient) : UserDetailsService {
    override fun loadUserByUsername(username: String): SecurityUser {
        val user = userClient.getUserByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        return SecurityUser(
            user.username,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        )
    }
}