package com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse

import com.google.gson.annotations.SerializedName


data class Links(
    @SerializedName("download")
    val download: String?,
    @SerializedName("download_location")
    val downloadLocation: String?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("self")
    val self: String?
)