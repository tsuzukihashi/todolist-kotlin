package com.example.todolist.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class TaskCreateForm {
    @NotBlank
    @Size(max = 20)
    var content: String? = null
}