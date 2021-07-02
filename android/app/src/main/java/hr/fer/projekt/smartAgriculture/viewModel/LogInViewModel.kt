package hr.fer.projekt.smartAgriculture.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.projekt.smartAgriculture.model.LoginModel
import hr.fer.projekt.smartAgriculture.model.TokenModel
import hr.fer.projekt.smartAgriculture.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: Repository) : ViewModel() {

    val responseLiveData: MutableLiveData<Response<TokenModel>> = MutableLiveData()

    fun login(loginModel: LoginModel) {
        viewModelScope.launch {
            val response = repository.login(loginModel)
            responseLiveData.value = response
        }
    }
}
