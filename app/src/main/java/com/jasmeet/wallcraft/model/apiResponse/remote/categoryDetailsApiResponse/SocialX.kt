package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class SocialX(
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("paypal_email")
    val paypalEmail: Any?,
    @SerializedName("portfolio_url")
    val portfolioUrl: Any?,
    @SerializedName("twitter_username")
    val twitterUsername: Any?
)