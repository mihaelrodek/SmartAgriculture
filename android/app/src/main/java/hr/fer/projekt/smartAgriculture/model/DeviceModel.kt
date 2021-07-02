package hr.fer.projekt.smartAgriculture.model

import java.io.Serializable

class DeviceModel(
    val id: Long,
    val devId: String
) : Serializable {
    override fun toString(): String {
        return devId
    }

    override fun equals(other: Any?): Boolean {
        return this.id == (other as DeviceModel).id
    }
}
