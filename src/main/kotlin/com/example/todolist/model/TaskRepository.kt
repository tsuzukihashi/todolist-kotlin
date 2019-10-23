package com.example.todolist.model

import com.example.todolist.Task

interface TaskRepository {
    fun create(content: String): Task
    fun update(task: Task)
    fun findAll(): List<Task>
    fun findById(id: Long): Task?
}