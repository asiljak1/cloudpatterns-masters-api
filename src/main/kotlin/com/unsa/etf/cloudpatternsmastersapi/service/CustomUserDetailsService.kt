package com.unsa.etf.cloudpatternsmastersapi.service

import com.unsa.etf.cloudpatternsmastersapi.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.User as SecurityUser
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): SecurityUser {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        return SecurityUser(
            user.username,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        )
    }
}