package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import com.google.gson.annotations.SerializedName

data class LinksXX(
    @SerializedName("html")
    val html: String?,
    @SerializedName("photos")
    val photos: String?,
    @SerializedName("self")
    val self: String?
)