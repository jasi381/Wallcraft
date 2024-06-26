package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class Result(
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
    val description: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("promoted_at")
    val promotedAt: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("sponsorship")
    val sponsorship: Any?,
    @SerializedName("tags")
    val tags: List<Tag>?,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissionsX?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("urls")
    val urls: UrlsX?,
    @SerializedName("user")
    val user: UserX?,
    @SerializedName("width")
    val width: Int?
)