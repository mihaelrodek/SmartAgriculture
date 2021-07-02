package hr.fer.projekt.smartAgriculture.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.projekt.smartAgriculture.model.MeasurementModel
import hr.fer.projekt.smartAgriculture.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MeasurementsViewModel(private val repository: Repository) : ViewModel() {

    val responseLiveData: MutableLiveData<Response<List<MeasurementModel>>> = MutableLiveData()

    val responseLiveDataGetLastMeasurements: MutableLiveData<Response<List<MeasurementModel>>> =
        MutableLiveData()

    fun getMeasurements(token: String) {
        viewModelScope.launch {
            val response = repository.getMeasurements(token)
            responseLiveData.value = response
        }
    }

    fun getLastMeasurements(token: String) {
        viewModelScope.launch {
            val response = repository.getLastMeasurements(token)
            responseLiveDataGetLastMeasurements.value = response
        }
    }
}