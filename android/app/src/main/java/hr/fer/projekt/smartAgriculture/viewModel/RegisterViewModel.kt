package hr.fer.projekt.smartAgriculture.viewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.projekt.smartAgriculture.model.RegistrationModel
import hr.fer.projekt.smartAgriculture.model.TokenModel
import hr.fer.projekt.smartAgriculture.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    val responseLiveData: MutableLiveData<Response<TokenModel>> = MutableLiveData()

    fun registerUser(registrationModel: RegistrationModel) {
        viewModelScope.launch {
            val response = repository.registerUser(registrationModel)
            responseLiveData.value = response
        }
    }
}
