package com.willyramad.lmovie.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willyramad.lmovie.R
import com.willyramad.lmovie.databinding.ItemFilmBinding
import com.willyramad.lmovie.model.FavotiteFilm
import com.willyramad.lmovie.model.Result

class AdapterFilmFavorite(var listFav : List<FavotiteFilm>) : RecyclerView.Adapter<AdapterFilmFavorite.ViewHolder>() {
    class ViewHolder (var binding : ItemFilmBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNama.text = listFav[position].title
        holder.binding.tvReting.text = listFav[position].reting
        holder.binding.tvdesc.text = listFav[position].deskripsi
        holder.binding.tvRilis.text = listFav[position].rilis
        Glide.with(holder.itemView.context).load(IMAGE_BASE+listFav[position].image).into(holder.binding.Img)
    }

    override fun getItemCount(): Int {
        return listFav.size
    }
}