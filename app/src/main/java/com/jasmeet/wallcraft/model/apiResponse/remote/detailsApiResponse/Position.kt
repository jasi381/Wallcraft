package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class Position(

    @SerializedName("latitude") var latitude: String? = null,
    @SerializedName("longitude") var longitude: String? = null

)