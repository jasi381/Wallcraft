package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class CoverPhoto(
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("alternative_slugs")
    val alternativeSlugs: AlternativeSlugs?,
    @SerializedName("asset_type")
    val assetType: String?,
    @SerializedName("blur_hash")
    val blurHash: String?,
    @SerializedName("breadcrumbs")
    val breadcrumbs: List<Any>?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("links")
    val links: LinksXX?,
    @SerializedName("promoted_at")
    val promotedAt: Any?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("sponsorship")
    val sponsorship: Any?,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissions?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("urls")
    val urls: UrlsX?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("width")
    val width: Int?
)