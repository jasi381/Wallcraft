package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class Results(

    @SerializedName("id") var id: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("published_at") var publishedAt: String?,
    @SerializedName("last_collected_at") var lastCollectedAt: String?,
    @SerializedName("updated_at") var updatedAt: String?,
    @SerializedName("featured") var featured: Boolean?,
    @SerializedName("total_photos") var totalPhotos: Int?,
    @SerializedName("private") var private: Boolean?,
    @SerializedName("share_key") var shareKey: String?,
    @SerializedName("tags") var tags: ArrayList<Tags>?,
    @SerializedName("links") var links: Links?,
    @SerializedName("user") var user: User?,
    @SerializedName("cover_photo") var coverPhoto: CoverPhoto?,
    @SerializedName("preview_photos") var previewPhotos: ArrayList<PreviewPhotos>?

)