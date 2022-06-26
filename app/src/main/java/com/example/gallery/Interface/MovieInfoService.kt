package com.example.gallery.Interface


import com.example.gallery.Bean.MovieInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface MovieInfoService {
    @GET
    fun movieInfoList(
        @Url url: String?
    ): Call<ArrayList<MovieInfo?>?>?
}