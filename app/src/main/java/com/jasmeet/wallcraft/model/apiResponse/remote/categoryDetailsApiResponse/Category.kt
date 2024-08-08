package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Category(
    @SerializedName("pretty_slug")
    val prettySlug: String?,
    @SerializedName("slug")
    val slug: String?
)