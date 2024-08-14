package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopicSubmissionsX(
    @SerializedName("architecture-interior")
    val architectureInterior: ArchitectureInterior?
)