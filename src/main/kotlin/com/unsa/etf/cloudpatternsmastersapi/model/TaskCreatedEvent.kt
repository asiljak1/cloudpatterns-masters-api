package com.unsa.etf.cloudpatternsmastersapi.model

import java.time.LocalDateTime

data class TaskCreatedEvent(
    val id: String,
    val title: String,
    val createdAt: LocalDateTime
)
