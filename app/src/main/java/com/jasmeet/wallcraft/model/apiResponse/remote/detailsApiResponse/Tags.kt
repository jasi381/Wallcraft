package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Tags(

    @SerializedName("type") var type: String? = null,
    @SerializedName("title") var title: String? = null

)