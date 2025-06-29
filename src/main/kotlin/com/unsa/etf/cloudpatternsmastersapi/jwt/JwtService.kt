package com.unsa.etf.cloudpatternsmastersapi.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.io.Decoders
import org.springframework.stereotype.Service
import java.util.*
@Service
class JwtService {

    private val secretKey = "mysecretkeymysecretkeymysecretkey1234" // mora biti 32 bajta (za HS256)

    fun extractUsername(token: String): String {
        val key = Keys.hmacShaKeyFor(secretKey.toByteArray())
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun isTokenValid(token: String, username: String): Boolean {
        return extractUsername(token) == username
    }
}
