package com.unsa.etf.cloudpatternsmastersapi.service

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import com.unsa.etf.cloudpatternsmastersapi.model.TaskCreatedEvent
import com.unsa.etf.cloudpatternsmastersapi.filters.TaskPipeline
import com.unsa.etf.cloudpatternsmastersapi.repository.TaskRepository
import com.unsa.etf.cloudpatternsmastersapi.repository.UserRepository
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
    private val taskPipeline: TaskPipeline,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    @CacheEvict("tasks", allEntries = true)
    fun createTask(task: Task, username: String): Task {
        val processedTask = taskPipeline.process(task, username)

        val user = userRepository.findByUsername(username)
        processedTask.user = user
        val saved = taskRepository.save(processedTask)

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