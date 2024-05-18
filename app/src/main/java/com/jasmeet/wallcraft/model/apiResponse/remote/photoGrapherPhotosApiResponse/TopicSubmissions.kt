package com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse

import com.google.gson.annotations.SerializedName


data class TopicSubmissions(
    @SerializedName("architecture-interior")
    val architectureInterior: ArchitectureInterior?,
    @SerializedName("interiors")
    val interiors: Interiors?,
    @SerializedName("nature")
    val nature: Nature?,
    @SerializedName("spring")
    val spring: Spring?,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns?,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers?
)