package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Social(
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("paypal_email")
    val paypalEmail: Any?,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    @SerializedName("twitter_username")
    val twitterUsername: String?
)