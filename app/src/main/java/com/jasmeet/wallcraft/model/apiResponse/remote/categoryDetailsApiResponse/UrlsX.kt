package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UrlsX(
    @SerializedName("full")
    val full: String?,
    @SerializedName("raw")
    val raw: String?,
    @SerializedName("regular")
    val regular: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("small_s3")
    val smallS3: String?,
    @SerializedName("thumb")
    val thumb: String?
)