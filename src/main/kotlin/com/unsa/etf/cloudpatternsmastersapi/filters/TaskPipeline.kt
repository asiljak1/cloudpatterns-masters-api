package com.unsa.etf.cloudpatternsmastersapi.filters

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import org.springframework.stereotype.Component

@Component
class TaskPipeline(
    private val filters: List<TaskFilter>
) {
    fun process(task: Task, username: String): Task {
        var current = task
        for (filter in filters) {
            current = filter.apply(current, username)
        }
        return current
    }
}