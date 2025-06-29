package com.unsa.etf.cloudpatternsmastersapi.repository

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, String>  {
    fun findAllByUserUsername(username: String): List<Task>
}
