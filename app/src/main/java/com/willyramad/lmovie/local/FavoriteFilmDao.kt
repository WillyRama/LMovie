package com.willyramad.lmovie.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.willyramad.lmovie.model.FavotiteFilm


@Dao
interface FavoriteFilmDao {
    @Insert
    fun addToFavorite(favotiteFilm: FavotiteFilm):Long

    @Query("SELECT * FROM FavotiteFilm")
    fun getFavoritFilm() : List<FavotiteFilm>

    //
    @Query("SELECT count(*) FROM FavotiteFilm WHERE FavotiteFilm.id = :id")
    fun checkFilm(id: Int) : Int
}