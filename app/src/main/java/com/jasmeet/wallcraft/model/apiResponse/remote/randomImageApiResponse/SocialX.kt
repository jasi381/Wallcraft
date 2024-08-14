package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SocialX(
    @SerializedName("instagram_username")
    val instagramUsername: Any?,
    @SerializedName("paypal_email")
    val paypalEmail: Any?,
    @SerializedName("portfolio_url")
    val portfolioUrl: Any?,
    @SerializedName("twitter_username")
    val twitterUsername: Any?
)