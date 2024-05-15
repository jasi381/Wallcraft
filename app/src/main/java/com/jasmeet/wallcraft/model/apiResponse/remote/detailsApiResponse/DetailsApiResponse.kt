package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import com.google.gson.annotations.SerializedName


data class DetailsApiResponse(

    @SerializedName("id") var id: String?,
    @SerializedName("slug") var slug: String?,
    @SerializedName("alternative_slugs") var alternativeSlugs: AlternativeSlugs?,
    @SerializedName("created_at") var createdAt: String?,
    @SerializedName("updated_at") var updatedAt: String?,
    @SerializedName("promoted_at") var promotedAt: String?,
    @SerializedName("width") var width: Int?,
    @SerializedName("height") var height: Int?,
    @SerializedName("color") var color: String?,
    @SerializedName("blur_hash") var blurHash: String?,
    @SerializedName("description") var descrciption: String?,
    @SerializedName("alt_description") var altDescription: String?,
    @SerializedName("breadcrumbs") var breadcrumbs: ArrayList<Breadcrumbs>?,
    @SerializedName("urls") var urls: Urls?,
    @SerializedName("links") var links: Links?,
    @SerializedName("likes") var likes: Int?,
    @SerializedName("liked_by_user") var likedByUser: Boolean?,
    @SerializedName("current_user_collections") var currentUserCollections: ArrayList<String>?,
    @SerializedName("sponsorship") var sponsorship: String?,
    @SerializedName("topic_submissions") var topicSubmissions: Any?,
    @SerializedName("asset_type") var assetType: String?,
    @SerializedName("user") var user: User?,
    @SerializedName("exif") var exif: Exif?,
    @SerializedName("location") var location: Location?,
    @SerializedName("meta") var meta: Meta?,
    @SerializedName("public_domain") var publicDomain: Boolean?,
    @SerializedName("tags") var tags: ArrayList<Tags>,
    @SerializedName("tags_preview") var tagsPreview: ArrayList<TagsPreview>?,
    @SerializedName("views") var views: Int?,
    @SerializedName("downloads") var downloads: Int?,
    @SerializedName("topics") var topics: ArrayList<Topics>?,
    @SerializedName("related_collections") var relatedCollections: RelatedCollections?

)