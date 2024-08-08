package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Health(
    @SerializedName("approved_on")
    val approvedOn: String?,
    @SerializedName("status")
    val status: String?
)