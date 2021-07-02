package hr.fer.projekt.smartAgriculture.model

data class BoundaryModel(
    val cultureId: Long,
    val minAirTemperature: Double,
    val maxAirTemperature: Double,
    val minSoilTemperature: Double,
    val maxSoilTemperature: Double,
    val minAirHumidity: Double,
    val maxAirHumidity: Double,
    val minSoilHumidity: Double,
    val maxSoilHumidity: Double,
    val minPressure: Double,
    val maxPressure: Double,
    val minLuminosity: Double,
    val maxLuminosity: Double
)