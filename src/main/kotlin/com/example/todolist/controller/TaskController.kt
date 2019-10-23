package com.example.todolist.controller

import com.example.todolist.Task
import com.example.todolist.model.TaskCreateForm
import com.example.todolist.model.TaskRepository
import com.example.todolist.model.TaskUpdateForm
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@Controller
@RequestMapping("tasks")
class TaskController(private val taskRepository: TaskRepository) {

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(): String = "task/not_found"

    @GetMapping("")
    fun index(model: Model): String {
        val tasks =  taskRepository.findAll()
        model.addAttribute("tasks", tasks)
        return "tasks/index"
    }

    @GetMapping("new")
    fun new(form: TaskCreateForm): String {
        return "tasks/new"
    }

    @PostMapping("")
    fun create(@Validated form: TaskCreateForm, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors())
            return "tasks/new"

        val content = requireNotNull(form.content)
        taskRepository.create(content)
        return "redirect:/tasks"
    }

    @GetMapping("{id}/edit")
    fun edit(@PathVariable("id") id: Long, form: TaskUpdateForm): String {
        val task = taskRepository.findById(id) ?: throw RuntimeException()
        form.content = task.content
        form.done = task.done
        return "tasks/edit"
    }

    @PostMapping("{id}")
    fun update(@PathVariable("id") id: Long,
               @Validated form: TaskUpdateForm,
               bindingResult: BindingResult): String {
        if (bindingResult.hasErrors())
            return "tasks/edit"

        val task = taskRepository.findById(id) ?: throw RuntimeException()
        val newTask = task.copy(content = requireNotNull(form.content), done = form.done)
        taskRepository.update(newTask)
        return "redirect:/tasks"
    }
}