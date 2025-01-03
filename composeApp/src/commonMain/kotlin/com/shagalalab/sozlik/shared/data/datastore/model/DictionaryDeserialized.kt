package com.shagalalab.sozlik.shared.data.datastore.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDeserialized(
    @SerialName("w")
    val word: String,
    @SerialName("t")
    val translation: String
)

