package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopicSubmissions(
    @SerializedName("architecture-interior")
    val architectureInterior: ArchitectureInterior?,
    @SerializedName("current-events")
    val currentEvents: CurrentEvents?,
    @SerializedName("3d-renders")
    val dRenders: DRenders?,
    @SerializedName("monochrome")
    val monochrome: Monochrome?,
    @SerializedName("nature")
    val nature: Nature?,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns?,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers?
)