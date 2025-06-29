package com.unsa.etf.cloudpatternsmastersapi.filters

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import com.unsa.etf.cloudpatternsmastersapi.repository.TaskRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MaxTaskLimitFilter(
    private val taskRepository: TaskRepository,
    @Value("\${config.max-tasks-per-user:5}") private val maxTasks: Int
) : TaskFilter {

    override fun apply(task: Task, username: String): Task {
        val count = taskRepository.findAllByUserUsername(username).size
        if (count >= maxTasks) {
            throw IllegalStateException("User has reached max number of tasks ($maxTasks).")
        }
        return task
    }
}