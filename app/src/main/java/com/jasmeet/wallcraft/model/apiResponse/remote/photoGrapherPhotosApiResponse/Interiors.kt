package com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Interiors(
    @SerializedName("approved_on")
    val approvedOn: String?,
    @SerializedName("status")
    val status: String?
)