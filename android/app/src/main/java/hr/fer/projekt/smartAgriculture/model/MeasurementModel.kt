package hr.fer.projekt.smartAgriculture.model

import java.util.*

data class MeasurementModel(
    val id: Long,
    val device: DeviceModel,
    val time: Date,
    val airHumidity: Double,
    val soilHumidity: Double,
    val airTemperature: Double,
    val soilTemperature: Double,
    val pressure: Double,
    val luminosity: Double
)
