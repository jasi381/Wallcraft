package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LinksXX(
    @SerializedName("html")
    val html: String?,
    @SerializedName("photos")
    val photos: String?,
    @SerializedName("self")
    val self: String?
)