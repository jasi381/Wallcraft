package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import com.google.gson.annotations.SerializedName

data class TopicSubmissions(

    @SerializedName("3d-renders")
    val renders: DRenders?,

    @SerializedName("black-and-white")
    val blackWhite: BlackAndWhite?,
    val experimental: Experimental?,
    val spirituality: Spirituality?,

    @SerializedName("street-photography")
    val streetPhotography: StreetPhotography?,

    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns?,
    val wallpapers: Wallpapers
)