package com.willyramad.lmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.willyramad.lmovie.R
import com.willyramad.lmovie.adapter.AdapterFilm
import com.willyramad.lmovie.databinding.FragmentHomeBinding
import com.willyramad.lmovie.viewmodel.UserViewModel
import com.willyramad.lmovie.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding : com.willyramad.lmovie.databinding.FragmentHomeBinding
    lateinit var adapterFilm: AdapterFilm
    lateinit var Nama : String
    lateinit var splash : String
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding =   FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel =ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        userViewModel.dataUser.observe(requireActivity()){
            Nama = it.nama
            splash = it.splash
        }
        binding.tvHeaderUser.text = "Selamat datang "+Nama

        binding.profil.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profilFragment)
        }
        binding.fav.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
        setDataFilm()
    }
    fun setDataFilm(){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelFilm::class.java)
        viewModel.getAllFilm().observe(viewLifecycleOwner, Observer {

            if (it != null){
                adapterFilm = AdapterFilm(it.results)
                binding.rvFilm.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                binding.rvFilm.adapter = adapterFilm
            }else{
                Toast.makeText(context, "Data Kosong", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.callAllFilm()
    }
}