package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import com.google.gson.annotations.SerializedName

data class Experimental(
    @SerializedName("approved_on")
    val approvedOn: String?,
    val status: String?
)