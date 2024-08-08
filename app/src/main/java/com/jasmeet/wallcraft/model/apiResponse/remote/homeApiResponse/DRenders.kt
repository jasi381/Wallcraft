package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DRenders(
    @SerializedName("approved_on")
    val approvedOn: String,
    val status: String
)