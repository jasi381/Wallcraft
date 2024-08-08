package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import androidx.annotation.Keep

@Keep
data class Breadcrumb(
    val index: Int?,
    val slug: String?,
    val title: String?,
    val type: String?
)