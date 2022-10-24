package com.willyramad.lmovie.service

import com.willyramad.lmovie.model.ResponFilmItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("3/movie/popular?api_key=0ba708223346c1eec013ffb82a653da7")
    fun getAllFilm() : Call<ResponFilmItem>
}