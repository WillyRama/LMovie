package com.willyramad.lmovie.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.willyramad.lmovie.model.FavotiteFilm


@Database(entities = [FavotiteFilm::class], version = 1)
abstract class FilmDabase : RoomDatabase()  {

    abstract fun filmDao(): FavoriteFilmDao
    companion object{
        private var INSTANCE : FilmDabase? = null

        fun getInstance(context : Context):FilmDabase? {
            if (INSTANCE == null){
                synchronized(FilmDabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FilmDabase::class.java,"favoritfilm.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}