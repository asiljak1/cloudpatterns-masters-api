package com.unsa.etf.cloudpatternsmastersapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class AppUser(
    @Id
    val username: String = "",
    val password: String = "",
    val role: String = "USER"
)
