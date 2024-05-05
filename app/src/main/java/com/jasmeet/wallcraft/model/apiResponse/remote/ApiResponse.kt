package com.jasmeet.wallcraft.model.apiResponse.remote

data class ApiResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)