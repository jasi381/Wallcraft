package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import androidx.annotation.Keep

@Keep
data class ProfileImage(
    val large: String?,
    val medium: String?,
    val small: String?
)