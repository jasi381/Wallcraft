package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Links(
    val download: String?,

    @SerializedName("download_location")
    val downloadLocation: String?,
    val html: String?,
    val self: String?
)