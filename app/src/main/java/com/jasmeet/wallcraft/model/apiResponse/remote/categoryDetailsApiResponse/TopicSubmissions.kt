package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("current-events")
    val currentEvents: CurrentEvents?,
    @SerializedName("experimental")
    val experimental: Experimental?,
    @SerializedName("health")
    val health: Health?,
    @SerializedName("people")
    val people: People?,
    @SerializedName("spirituality")
    val spirituality: Spirituality?,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns?,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers?
)