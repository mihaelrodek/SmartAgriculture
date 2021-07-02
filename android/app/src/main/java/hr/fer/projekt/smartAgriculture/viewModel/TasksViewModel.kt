package hr.fer.projekt.smartAgriculture.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.fer.projekt.smartAgriculture.database.DatabaseHandler
import hr.fer.projekt.smartAgriculture.model.TaskModel
import hr.fer.projekt.smartAgriculture.model.User

class TasksViewModel(private val databaseHandler: DatabaseHandler) : ViewModel() {

    val tasksList: MutableLiveData<ArrayList<TaskModel>> = MutableLiveData()

    fun getTasks() {
        tasksList.value = databaseHandler.getTasks(user = User.user.username)
    }

    fun addTask(taskModel: TaskModel) {
        databaseHandler.addTask(task = taskModel)
    }

    fun deleteTask(taskModel: TaskModel): Int {
        val success = databaseHandler.deleteTask(task = taskModel)
        getTasks()
        return success
    }

    fun updateTask(taskModel: TaskModel): Int {
        val success = databaseHandler.updateTask(task = taskModel)
        getTasks()
        return success
    }
}