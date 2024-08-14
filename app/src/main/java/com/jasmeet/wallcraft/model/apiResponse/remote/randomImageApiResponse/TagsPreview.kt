package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TagsPreview(
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)