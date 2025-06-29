package com.unsa.etf.cloudpatternsmastersapi.filters

import com.unsa.etf.cloudpatternsmastersapi.model.Task

interface TaskFilter {
    fun apply(task: Task, username: String): Task
}