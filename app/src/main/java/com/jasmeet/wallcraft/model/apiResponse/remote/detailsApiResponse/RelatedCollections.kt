package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class RelatedCollections(

    @SerializedName("total") var total: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("results") var results: ArrayList<Results> = arrayListOf()

)