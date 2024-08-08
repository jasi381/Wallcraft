package com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostedByApiResponse(
    val results: List<Result>?,
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)