package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class TagsPreview(

    @SerializedName("type") var type: String? = null,
    @SerializedName("title") var title: String? = null

)