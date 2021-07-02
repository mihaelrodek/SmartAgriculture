package hr.fer.projekt.smartAgriculture.model

import java.io.Serializable

data class TaskModel(
    var taskId: Int,
    val task: String,
    val user: String
) : Serializable