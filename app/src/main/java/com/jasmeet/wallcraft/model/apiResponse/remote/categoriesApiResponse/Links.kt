package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Links(
    @SerializedName("download")
    val download: String?,
    @SerializedName("download_location")
    val downloadLocation: String?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("self")
    val self: String?
)