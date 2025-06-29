package com.unsa.etf.cloudpatternsmastersapi.repository

import com.unsa.etf.cloudpatternsmastersapi.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<AppUser, String> {
    fun findByUsername(username: String): AppUser?
}
