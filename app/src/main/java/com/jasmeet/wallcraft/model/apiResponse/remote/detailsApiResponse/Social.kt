package com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Social(

    @SerializedName("instagram_username") var instagramUsername: String? = null,
    @SerializedName("portfolio_url") var portfolioUrl: String? = null,
    @SerializedName("twitter_username") var twitterUsername: String? = null,
    @SerializedName("paypal_email") var paypalEmail: String? = null

)