package com.willyramad.lmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.willyramad.lmovie.R
import com.willyramad.lmovie.adapter.AdapterFilmFavorite
import com.willyramad.lmovie.databinding.FragmentFavoriteBinding
import com.willyramad.lmovie.local.FilmDabase
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    lateinit var binding : FragmentFavoriteBinding
    private var filmdb : FilmDabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmdb = FilmDabase.getInstance(requireActivity())
        binding.rvFav.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        GlobalScope.launch {
            val listFav = filmdb?.filmDao()?.getFavoritFilm()
            requireActivity().runOnUiThread {
                listFav.let {
                    val adapter = AdapterFilmFavorite(it!!)
                    rvFav.adapter = adapter
                }
            }
        }
    }
}