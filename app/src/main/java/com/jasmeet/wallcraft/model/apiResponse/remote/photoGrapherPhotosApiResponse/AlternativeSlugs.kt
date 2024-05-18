package com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse


import com.google.gson.annotations.SerializedName


data class AlternativeSlugs(
    @SerializedName("de")
    val de: String?,
    @SerializedName("en")
    val en: String?,
    @SerializedName("es")
    val es: String?,
    @SerializedName("fr")
    val fr: String?,
    @SerializedName("it")
    val `it`: String?,
    @SerializedName("ja")
    val ja: String?,
    @SerializedName("ko")
    val ko: String?,
    @SerializedName("pt")
    val pt: String?
)