package com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse

import com.google.gson.annotations.SerializedName

data class PostedByApiResponse(
    val results: List<Result>?,
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)