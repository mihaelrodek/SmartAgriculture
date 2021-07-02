package hr.fer.projekt.smartAgriculture.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.projekt.smartAgriculture.model.CultureModel
import hr.fer.projekt.smartAgriculture.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class CultureViewModel(private val repository: Repository) : ViewModel() {

    val responseLiveData: MutableLiveData<Response<List<CultureModel>>> = MutableLiveData()

    fun addCulture(token: String, cultureModel: CultureModel) {
        viewModelScope.launch {
            repository.addCulture(token, cultureModel)
        }
    }

    fun deleteCulture(token: String, id: Long) {
        viewModelScope.launch {
            repository.deleteCulture(token, id)
        }
    }

    fun getCultures(token: String) {
        viewModelScope.launch {
            val response = repository.getAllCultures(token)
            responseLiveData.value = response
        }
    }


}
