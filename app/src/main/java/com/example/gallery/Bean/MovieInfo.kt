package com.example.gallery.Bean


import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MovieInfo : Serializable {
    var name: String? = null
    var url: Url? = null

    @SerializedName("timestamp")
    var timeStamp: String? = null
}