package com.unsa.etf.cloudpatternsmastersapi.controller

import com.unsa.etf.cloudpatternsmastersapi.repository.UserRepository
import com.unsa.etf.cloudpatternsmastersapi.jwt.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    data class LoginRequest(val username: String, val password: String)
    data class LoginResponse(val token: String)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        val user = userRepository.findByUsername(request.username)
            ?: return ResponseEntity.status(401).body("User not found")

        if (user.password != request.password) {
            return ResponseEntity.status(401).body("Invalid password")
        }

        val token = jwtService.generateToken(user.username)
        return ResponseEntity.ok(LoginResponse(token))
    }
}
