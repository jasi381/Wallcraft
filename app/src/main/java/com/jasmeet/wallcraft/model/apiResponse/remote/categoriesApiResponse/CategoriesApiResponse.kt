package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import com.google.gson.annotations.SerializedName

data class CategoriesApiResponse(

    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto?,
    @SerializedName("current_user_contributions")
    val currentUserContributions: List<Any>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("ends_at")
    val endsAt: String?,
    @SerializedName("featured")
    val featured: Boolean?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("links")
    val links: LinksXX?,
    @SerializedName("only_submissions_after")
    val onlySubmissionsAfter: Any?,
    @SerializedName("owners")
    val owners: List<Owner>?,
    @SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhoto>?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("starts_at")
    val startsAt: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("total_current_user_submissions")
    val totalCurrentUserSubmissions: Any?,
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("visibility")
    val visibility: String?
)