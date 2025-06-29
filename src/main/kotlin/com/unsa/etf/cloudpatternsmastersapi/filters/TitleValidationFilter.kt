package com.unsa.etf.cloudpatternsmastersapi.filters

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import org.springframework.stereotype.Component

@Component
class TitleValidationFilter : TaskFilter {
    override fun apply(task: Task, username: String): Task {
        if (task.title.isBlank()) {
            throw IllegalArgumentException("Title cannot be blank.")
        }
        return task
    }
}