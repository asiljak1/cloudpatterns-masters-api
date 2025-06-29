package com.unsa.etf.cloudpatternsmastersapi.service

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import com.unsa.etf.cloudpatternsmastersapi.repository.TaskRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class TaskQueryService(private val taskRepository: TaskRepository) {

    fun getAllTasksByUserUsername(username: String): List<Task> {
        println(username)
        return taskRepository.findAllByUserUsername(username)
    }
    fun getTaskById(id: String): Task? = taskRepository.findById(id).orElse(null)
}