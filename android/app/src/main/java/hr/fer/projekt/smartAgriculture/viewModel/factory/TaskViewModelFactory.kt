package hr.fer.projekt.smartAgriculture.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.database.DatabaseHandler

import hr.fer.projekt.smartAgriculture.viewModel.TasksViewModel

class TaskViewModelFactory(private val databaseHandler: DatabaseHandler) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(databaseHandler) as T
    }
}