package com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse

import com.google.gson.annotations.SerializedName

data class Photo(

    @SerializedName("asset_type")
    val assetType: String?,

    @SerializedName("blur_hash")
    val blurHash: String?,

    @SerializedName("created_at")
    val createdAt: String?,
    val id: String?,
    val slug: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,
    val urls: Urls?
)