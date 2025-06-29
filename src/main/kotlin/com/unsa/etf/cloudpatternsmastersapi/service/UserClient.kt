package com.unsa.etf.cloudpatternsmastersapi.service

import com.unsa.etf.cloudpatternsmastersapi.model.AppUser
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
class UserClient {

    private val restTemplate = RestTemplate()
    private val userServiceUrl = "http://localhost:8081/users" // user-service port

    fun getUserByUsername(username: String): AppUser? {
        return try {
            val headers = HttpHeaders()

            // Dohvati trenutni HTTP request da izvučeš Authorization header
            val currentRequest = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
            val authHeader = currentRequest?.getHeader("Authorization")

            if (!authHeader.isNullOrBlank()) {
                headers.set("Authorization", authHeader)
            }

            val entity = HttpEntity<String>(headers)

            val response = restTemplate.exchange(
                "$userServiceUrl/$username",
                HttpMethod.GET,
                entity,
                AppUser::class.java
            )

            response.body
        } catch (ex: Exception) {
            null
        }
    }
}
