package com.willyramad.lmovie.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willyramad.lmovie.R
import com.willyramad.lmovie.databinding.ItemFilmBinding
import com.willyramad.lmovie.model.Result

class AdapterFilm(var listFilm : List<Result>) : RecyclerView.Adapter<AdapterFilm.ViewHolder>() {
    class ViewHolder (var binding : ItemFilmBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindFilm(result: Result){
            binding.tvNama.text = result.name
            binding.tvRilis.text = result.firstAirDate
            binding.tvReting.text = result.voteAverage.toString()
            binding.tvdesc.text = result.overview
            Glide.with(itemView).load(IMAGE_BASE + result.posterPath).into(binding.Img)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilm(listFilm[position])
        holder.binding.crFilm.setOnClickListener {
            val bundle = Bundle()
            val pos = listFilm[position]
            bundle.putString("judul", pos.name)
            bundle.putString("rilis", pos.firstAirDate)
            bundle.putString("detail", pos.overview)
            bundle.putString("reting", pos.voteAverage.toString())
            bundle.putString("img", pos.posterPath)
            Navigation.findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_detailFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }
}