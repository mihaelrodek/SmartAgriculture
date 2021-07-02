package hr.fer.projekt.smartAgriculture.viewModel

import android.util.Log
import android.util.Log.WARN
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.projekt.smartAgriculture.model.BoundaryModel
import hr.fer.projekt.smartAgriculture.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class CultureBoundariesViewModel(private val repository: Repository) : ViewModel() {

    val responseLiveData: MutableLiveData<Response<Any>> = MutableLiveData()

    fun addBoundary(token: String, boundaryModel: BoundaryModel) {
        viewModelScope.launch {
            repository.addBoundary(token, boundaryModel)
        }
    }
}
