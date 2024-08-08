package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Breadcrumb(
    @SerializedName("index")
    val index: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)