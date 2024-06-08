package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class BackToSchool(
    @SerializedName("approved_on")
    val approvedOn: String?,
    @SerializedName("status")
    val status: String?
)