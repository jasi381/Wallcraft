package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RelatedCollections(

    @SerializedName("total") var total: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("results") var results: ArrayList<Results> = arrayListOf()

)