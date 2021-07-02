package hr.fer.projekt.smartAgriculture.model

import java.io.Serializable

data class CultureModel(
        val cultureId: Long,
        val title: String,
        val devices: List<DeviceModel>,
        val description: String
) : Serializable
