package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class Location(

    @SerializedName("name") var name: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("position") var position: Position? = Position()

)