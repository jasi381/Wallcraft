package com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("animals")
    val animals: Animals?,
    @SerializedName("architecture-interior")
    val architectureInterior: ArchitectureInterior?,
    @SerializedName("archival")
    val archival: Archival?,
    @SerializedName("business-work")
    val businessWork: BusinessWork?,
    @SerializedName("current-events")
    val currentEvents: CurrentEvents?,
    @SerializedName("3d-renders")
    val dRenders: DRenders?,
    @SerializedName("experimental")
    val experimental: Experimental?,
    @SerializedName("fashion-beauty")
    val fashionBeauty: FashionBeauty?,
    @SerializedName("film")
    val film: Film?,
    @SerializedName("food-drink")
    val foodDrink: FoodDrink?,
    @SerializedName("greener-cities")
    val greenerCities: GreenerCities?,
    @SerializedName("health")
    val health: Health?,
    @SerializedName("nature")
    val nature: Nature?,
    @SerializedName("people")
    val people: People?,
    @SerializedName("spirituality")
    val spirituality: Spirituality?,
    @SerializedName("sports")
    val sports: Sports?,
    @SerializedName("street-photography")
    val streetPhotography: StreetPhotography?,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns?,
    @SerializedName("travel")
    val travel: Travel?,
    @SerializedName("ugc")
    val ugc: Ugc?,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers?
)