package com.jasmeet.wallcraft.model.userInfo

import androidx.annotation.Keep

@Keep
data class UserInfo(
    val name: String,
    val email: String,
    val uid: String,
    val imgUrl: String
) {
    constructor() : this("", "", "", "")
}