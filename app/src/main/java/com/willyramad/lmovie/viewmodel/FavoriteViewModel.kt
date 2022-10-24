package com.willyramad.lmovie.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.willyramad.lmovie.local.FavoriteFilmDao
import com.willyramad.lmovie.model.FavotiteFilm
import com.willyramad.lmovie.local.FilmDabase

class FavoriteViewModel(context: Context): ViewModel() {
    private var filmfavDao : FavoriteFilmDao? =null
    private var filmFavDb : FilmDabase? = null

    lateinit var liveDataListFav : MutableLiveData<List<FavotiteFilm>>

    init {
        filmfavDao = filmFavDb!!.filmDao()
        filmFavDb = FilmDabase.getInstance(context)
    }
    fun getliveDataFilmFav(): MutableLiveData<List<FavotiteFilm>>{
        return liveDataListFav
    }
}