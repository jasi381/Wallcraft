package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class Tags(

    @SerializedName("type") var type: String? = null,
    @SerializedName("title") var title: String? = null

)