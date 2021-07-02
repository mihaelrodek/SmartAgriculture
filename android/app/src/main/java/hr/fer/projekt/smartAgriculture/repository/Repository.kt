package hr.fer.projekt.smartAgriculture.repository

import hr.fer.projekt.smartAgriculture.api.Retrofit
import hr.fer.projekt.smartAgriculture.model.*
import retrofit2.Response
import java.net.URI

class Repository {

    suspend fun login(loginModel: LoginModel): Response<TokenModel> {
        return Retrofit.api.login(loginModel)
    }

    suspend fun getMeasurements(token: String): Response<List<MeasurementModel>> {
        return Retrofit.api.getMeasurements(token)
    }

    suspend fun getLastMeasurements(token: String): Response<List<MeasurementModel>> {
        return Retrofit.api.getLastMeasurements(token)
    }

    suspend fun registerUser(registrationModel: RegistrationModel): Response<TokenModel> {
        return Retrofit.api.registerUser(registrationModel)
    }

    suspend fun addCulture(token: String, cultureModel: CultureModel)/*: Response<URI>*/ {
        return Retrofit.api.addCulture(token, cultureModel)
    }

    suspend fun getAllCultures(token: String): Response<List<CultureModel>> {
        return Retrofit.api.getAllCultures(token)
    }

    suspend fun getAllDevices(token: String): Response<List<DeviceModel>> {
        return Retrofit.api.getAllDevices(token)
    }

    suspend fun deleteCulture(token: String, id: Long): Response<Any>? {
        return Retrofit.api.deleteCulture(token, id)
    }

    suspend fun addDeviceToCulture(token: String, id: Long, devId: Long) {
        return Retrofit.api.addDeviceToCulture(token, id, devId)
    }

    suspend fun deleteDeviceFromCulture(token: String, cultureId: Long, devId: Long): Response<Any>? {
        return Retrofit.api.deleteDeviceFromCulture(token, cultureId, devId)
    }

    suspend fun getAllNotifications(token: String): Response<List<NotificationModel>> {
        return Retrofit.api.getAllNotifications(token)
    }

    suspend fun addBoundary(token: String, boundaryModel: BoundaryModel)/*: Response<Any>*/ {
        return Retrofit.api.addBoundary(token, boundaryModel)
    }

    suspend fun getBoundary(token: String, cultureId: Long): Response<BoundaryModel> {
        return Retrofit.api.getBoundaries(token, cultureId)
    }
}