package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Monochrome(
    @SerializedName("status")
    val status: String?
)