package com.unsa.etf.cloudpatternsmastersapi.service

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import com.unsa.etf.cloudpatternsmastersapi.model.TaskCreatedEvent
import com.unsa.etf.cloudpatternsmastersapi.repository.TaskRepository
import com.unsa.etf.cloudpatternsmastersapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@RefreshScope
@Service
class  TaskCommandService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    @Value("\${limits.max-tasks-per-user}")
    private var maxTasksPerUser: Int = 5

    @CacheEvict("tasks", allEntries = true)
    fun createTask(task: Task, username: String): Task {
        val userTasks = taskRepository.findAllByUserUsername(username)

        if (userTasks.size >= maxTasksPerUser) {
            throw IllegalStateException("Korisnik je dosegao maksimalan broj taskova: $maxTasksPerUser")
        }

        val user = userRepository.findByUsername(username)
        task.user = user
        val saved = taskRepository.save(task)

        val event = TaskCreatedEvent(saved.id!!, saved.title, saved.createdAt)
        kafkaTemplate.send("task-created-topic", event)

        return saved
    }

    fun updateTask(task: Task): Task? {
        val existingTask = taskRepository.findById(task.id!!).orElse(null) ?: return null
        val updatedTask = existingTask.copy(title = task.title, description = task.description)
        return taskRepository.save(updatedTask)
    }

    @CacheEvict("tasks", allEntries = true)
    fun deleteTask(id: String) {
        taskRepository.deleteById(id)
    }

    @CacheEvict("tasks", allEntries = true)
    fun markComplete(id: String): Task? {
        val existingTask = taskRepository.findById(id).orElse(null) ?: return null
        if (existingTask.completed == true) return existingTask

        val updated = existingTask.copy(
            completed = true,
            updatedAt = LocalDateTime.now()
        )
        return taskRepository.save(updated)
    }
}