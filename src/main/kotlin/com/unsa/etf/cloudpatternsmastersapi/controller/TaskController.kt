package com.unsa.etf.cloudpatternsmastersapi.controller

import com.unsa.etf.cloudpatternsmastersapi.model.Task
import com.unsa.etf.cloudpatternsmastersapi.service.TaskCommandService
import com.unsa.etf.cloudpatternsmastersapi.service.TaskQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val commandService: TaskCommandService,
    private val queryService: TaskQueryService
) {

    @GetMapping
    fun getAllTasks(principal: Principal): List<Task> = queryService.getAllTasksByUserUsername(principal.name)

    @PostMapping
    fun createTask(@RequestBody task: Task, principal: Principal): Task = commandService.createTask(task, principal.name)

    @PutMapping
    fun updateTask(@RequestBody task: Task): Task? = commandService.updateTask(task)

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: String): Task? =
        queryService.getTaskById(id)

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: String) {
        commandService.deleteTask(id)
    }

    @PatchMapping("/{id}/complete")
    fun markComplete(@PathVariable id: String): ResponseEntity<Task> {
        val updated = commandService.markComplete(id)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity.notFound().build()
    }
}
