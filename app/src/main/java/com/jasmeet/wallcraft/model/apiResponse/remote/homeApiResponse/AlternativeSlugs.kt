package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import com.google.gson.annotations.SerializedName

data class AlternativeSlugs(
    val de: String?,
    val en: String?,
    val es: String?,
    val fr: String?,

    @SerializedName("it")
    val ita: String?,

    val ja: String?,
    val ko: String?,
    val pt: String?
)