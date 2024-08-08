package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Ancestry(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("subcategory")
    val subcategory: Subcategory?,
    @SerializedName("type")
    val type: Type?
)