package com.willyramad.lmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.willyramad.lmovie.R
import com.willyramad.lmovie.adapter.IMAGE_BASE
import com.willyramad.lmovie.databinding.FragmentDetailBinding
import com.willyramad.lmovie.local.FavoriteFilmDao
import com.willyramad.lmovie.local.FilmDabase
import com.willyramad.lmovie.model.FavotiteFilm
import com.willyramad.lmovie.model.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private var filmDao : FavoriteFilmDao? = null
    private var filmDb : FilmDabase? = null
    private var id : Int? =null

    companion object{
        const val  EXTRA_ID = "extra_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
        filmDb = FilmDabase.getInstance(requireActivity())
        filmDao = filmDb?.filmDao()
        id = requireActivity().intent.getIntExtra(EXTRA_ID,0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gambar = arguments?.getString("img")
        val rilis = arguments?.getString("rilis")
        val desc = arguments?.getString("detail")
        val jud = arguments?.getString("judul")
        val popular = arguments?.getString("reting")
        Glide.with(requireActivity()).load(IMAGE_BASE + gambar).into(binding.Img)


        val detailfav = requireActivity().intent.getSerializableExtra("detailfav") as Result
        binding.tvNama.text = "Title :"+ jud
        binding.tvRilis.text = "Release :"+ rilis
        binding.tvdesc.text = "Description :\n"+ desc
        binding.tvReting.text = "Rating :"+ popular

        binding.fav.setOnClickListener {
            GlobalScope.async {
                val fav = requireActivity().intent.getSerializableExtra("detailfav") as Result?
                val idd = detailfav.id
                val judul = detailfav.name
                val ril = detailfav.firstAirDate
                val ret = detailfav.voteAverage.toString()
                val pop = detailfav.popularity.toString()
                val img = detailfav.posterPath
                val favorite = filmDb?.filmDao()?.addToFavorite(FavotiteFilm(idd,judul,ril,ret,pop,img))
                requireActivity().runOnUiThread {
                    if (favorite !=0.toLong()) {
                        Toast.makeText(
                            requireActivity(),
                            "Berhasil tambah Ke Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(requireActivity(), "Gagal menambah ke favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}