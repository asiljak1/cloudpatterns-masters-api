package com.unsa.etf.cloudpatternsmastersapi.service

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import com.unsa.etf.cloudpatternsmastersapi.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {
    fun getAllTasks(): List<Task> = taskRepository.findAll()

    fun createTask(task: Task): Task = taskRepository.save(task)
}
