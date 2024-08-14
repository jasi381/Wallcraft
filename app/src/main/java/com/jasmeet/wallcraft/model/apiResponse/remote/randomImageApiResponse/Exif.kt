package com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Exif(
    @SerializedName("aperture")
    val aperture: Any?,
    @SerializedName("exposure_time")
    val exposureTime: Any?,
    @SerializedName("focal_length")
    val focalLength: Any?,
    @SerializedName("iso")
    val iso: Any?,
    @SerializedName("make")
    val make: Any?,
    @SerializedName("model")
    val model: Any?,
    @SerializedName("name")
    val name: Any?
)