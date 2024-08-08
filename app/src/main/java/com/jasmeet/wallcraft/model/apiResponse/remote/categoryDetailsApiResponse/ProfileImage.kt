package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileImage(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?
)