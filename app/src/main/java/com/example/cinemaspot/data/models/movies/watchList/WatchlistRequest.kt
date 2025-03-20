package com.example.cinemaspot.data.models.movies.watchList

import com.google.gson.annotations.SerializedName

data class WatchlistRequest(
    @SerializedName("media_type") val mediaType: String = "movie",
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("watchlist") val isWatchlist: Boolean = true
)