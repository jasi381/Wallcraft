package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class UserX(
    @SerializedName("accepted_tos")
    val acceptedTos: Boolean?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("for_hire")
    val forHire: Boolean?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("links")
    val links: LinksXX?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("portfolio_url")
    val portfolioUrl: Any?,
    @SerializedName("profile_image")
    val profileImage: ProfileImage?,
    @SerializedName("social")
    val social: SocialX?,
    @SerializedName("total_collections")
    val totalCollections: Int?,
    @SerializedName("total_illustrations")
    val totalIllustrations: Int?,
    @SerializedName("total_likes")
    val totalLikes: Int?,
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("total_promoted_illustrations")
    val totalPromotedIllustrations: Int?,
    @SerializedName("total_promoted_photos")
    val totalPromotedPhotos: Int?,
    @SerializedName("twitter_username")
    val twitterUsername: Any?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?
)