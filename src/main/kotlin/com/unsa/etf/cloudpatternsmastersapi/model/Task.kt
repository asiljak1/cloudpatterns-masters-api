package com.unsa.etf.cloudpatternsmastersapi.model

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    val title: String = "",
    val description: String? = "",
    val completed: Boolean? = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: AppUser? = null
) : Serializable
