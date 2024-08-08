package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GreenerCities(
    @SerializedName("status")
    val status: String?
)