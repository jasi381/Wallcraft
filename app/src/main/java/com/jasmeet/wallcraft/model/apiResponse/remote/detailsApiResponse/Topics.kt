package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import androidx.annotation.Keep

@Keep
data class Topics(
    val id: String?,
    val title: String?,
    val slug: String?,
    val visibility: String?,
)