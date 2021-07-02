package hr.fer.projekt.smartAgriculture.model

import kotlin.reflect.KProperty

object User {
    var user : TokenModel by lazy {
        TokenModel("", "")
    }

    private operator fun <T> Lazy<T>.setValue(user: User, property: KProperty<*>, tokenModel: TokenModel) {
        user.user = tokenModel
    }
}
