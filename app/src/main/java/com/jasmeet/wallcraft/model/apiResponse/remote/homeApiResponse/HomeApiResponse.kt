package com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse

import com.google.gson.annotations.SerializedName

data class HomeApiResponse(

    @SerializedName("alt_description")
    val altDescription: String?,

    @SerializedName("alternative_slugs")
    val alternativeSlugs: AlternativeSlugs?,

    @SerializedName("asset_type")
    val assetType: String?,

    @SerializedName("blur_hash")
    val blurHash: String?,

    val breadcrumbs: List<Breadcrumb>?,
    val color: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>?,

    val description: String?,
    val height: Int?,
    val id: String?,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,

    val likes: Int?,
    val links: Links?,

    @SerializedName("promoted_at")
    val promotedAt: String?,
    val slug: String?,
    val sponsorship: Any,

    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissions?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    val urls: Urls,
    val user: User?,
    val width: Int?
)