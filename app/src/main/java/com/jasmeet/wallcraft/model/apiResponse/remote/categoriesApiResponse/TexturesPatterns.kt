package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import com.google.gson.annotations.SerializedName

data class TexturesPatterns(
    @SerializedName("approved_on")
    val approvedOn: String?,
    @SerializedName("status")
    val status: String?
)