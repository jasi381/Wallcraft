package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Wallpapers(
    @SerializedName("approved_on")
    val approvedOn: String?,
    @SerializedName("status")
    val status: String?
)