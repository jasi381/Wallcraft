package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class Breadcrumbs(

    @SerializedName("slug") var slug: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("index") var index: Int?,
    @SerializedName("type") var type: String?

)