package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RandomImageApiResponse(
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
    @SerializedName("downloads")
    val downloads: Int?,
    @SerializedName("exif")
    val exif: Exif?,
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
    @SerializedName("location")
    val location: Location?,
    @SerializedName("meta")
    val meta: Meta?,
    @SerializedName("promoted_at")
    val promotedAt: String?,
    @SerializedName("public_domain")
    val publicDomain: Boolean?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("sponsorship")
    val sponsorship: Any?,
    @SerializedName("tags")
    val tags: List<Tag>?,
    @SerializedName("tags_preview")
    val tagsPreview: List<TagsPreview>?,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissionsX?,
    @SerializedName("topics")
    val topics: List<Topic>?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("urls")
    val urls: UrlsX?,
    @SerializedName("user")
    val user: UserX?,
    @SerializedName("views")
    val views: Int?,
    @SerializedName("width")
    val width: Int?
)