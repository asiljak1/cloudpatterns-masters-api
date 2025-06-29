package com.unsa.etf.cloudpatternsmastersapi.filters

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MetadataEnrichmentFilter : TaskFilter {
    override fun apply(task: Task, username: String): Task {
        return task.copy(
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
    }
}