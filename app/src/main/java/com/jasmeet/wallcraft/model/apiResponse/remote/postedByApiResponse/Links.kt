package com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse

import androidx.annotation.Keep

@Keep
data class Links(
    val followers: String?,
    val following: String?,
    val html: String?,
    val likes: String?,
    val photos: String?,
    val portfolio: String?,
    val self: String?
)