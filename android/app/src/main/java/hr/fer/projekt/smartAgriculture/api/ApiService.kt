package hr.fer.projekt.smartAgriculture.api

import hr.fer.projekt.smartAgriculture.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body loginModel: LoginModel) : Response<TokenModel>

    @GET("/api/measurement/all")
    suspend fun getMeasurements(@Header("Authorization") token: String) : Response<List<MeasurementModel>>

    @GET("/api/measurement/last")
    suspend fun getLastMeasurements(@Header("Authorization") token: String) : Response<List<MeasurementModel>>

    @POST("/api/auth/register")
    suspend fun registerUser(@Body registrationModel: RegistrationModel) : Response<TokenModel>

    @POST("/api/culture/add")
    suspend fun addCulture(@Header("Authorization") token: String, @Body cultureModel: CultureModel)

    @GET("/api/culture/all")
    suspend fun getAllCultures(@Header("Authorization") token: String) : Response<List<CultureModel>>

    @GET("/api/devices")
    suspend fun getAllDevices(@Header("Authorization") token: String) : Response<List<DeviceModel>>

    @DELETE("/api/culture/delete/{id}")
    suspend fun deleteCulture(@Header("Authorization") token: String, @Path("id") id: Long): Response<Any>?

    @POST("/api/culture/{id}/devices/add/{deviceId}")
    suspend fun addDeviceToCulture(@Header("Authorization") token: String, @Path("id") id: Long, @Path("deviceId") deviceId: Long)

    @DELETE("api/culture/{cultureId}/devices/delete/{devId}")
    suspend fun deleteDeviceFromCulture(@Header("Authorization") token: String, @Path("cultureId") cultureId: Long, @Path("devId") deviceId: Long) : Response<Any>?

    @GET("/api/boundaries/{cultureId}")
    suspend fun getBoundaries(@Header("Authorization") token: String, @Path("cultureId") cultureId: Long) : Response<BoundaryModel>

    @POST("/api/boundaries")
    suspend fun addBoundary(@Header("Authorization") token: String, @Body boundaryModel: BoundaryModel)

    @GET("/api/notifications")
    suspend fun getAllNotifications(@Header("Authorization") token: String) : Response<List<NotificationModel>>

}