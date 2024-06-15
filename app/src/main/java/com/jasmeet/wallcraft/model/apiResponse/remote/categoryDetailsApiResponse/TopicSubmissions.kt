package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("health")
    val health: Health?
)