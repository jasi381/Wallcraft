package com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse


import com.google.gson.annotations.SerializedName

data class TopicSubmissionsX(
    @SerializedName("back-to-school")
    val backToSchool: BackToSchool?,
    @SerializedName("business-work")
    val businessWork: BusinessWork?,
    @SerializedName("digital-nomad")
    val digitalNomad: DigitalNomad?,
    @SerializedName("fashion-beauty")
    val fashionBeauty: FashionBeauty?,
    @SerializedName("food-drink")
    val foodDrink: FoodDrink?,
    @SerializedName("friends")
    val friends: Friends?,
    @SerializedName("health")
    val health: Health?,
    @SerializedName("people")
    val people: People?,
    @SerializedName("spirituality")
    val spirituality: Spirituality?,
    @SerializedName("work")
    val work: Work?,
    @SerializedName("work-from-home")
    val workFromHome: WorkFromHome?
)