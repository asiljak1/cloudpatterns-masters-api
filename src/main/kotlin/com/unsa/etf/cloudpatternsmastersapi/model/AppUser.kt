package com.unsa.etf.cloudpatternsmastersapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class AppUser(
    @Id
    var username: String = "",
    val password: String = "",
    val role: String = "USER",

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var tasks: List<Task> = mutableListOf()
) {
    override fun toString(): String {
        return username
    }
}
