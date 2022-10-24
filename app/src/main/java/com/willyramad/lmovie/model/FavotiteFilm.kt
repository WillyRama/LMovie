package com.willyramad.lmovie.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class FavotiteFilm(
    @PrimaryKey
    var id : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "rilis")
    var rilis : String,
    @ColumnInfo(name = "deskripsi")
    var deskripsi : String,
    @ColumnInfo(name = "reting")
    var reting : String,
    @ColumnInfo(name = "image")
    var image : String
):Parcelable
