package com.willyramad.lmovie.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.willyramad.lmovie.R
import com.willyramad.lmovie.databinding.FragmentSplashBinding
import com.willyramad.lmovie.viewmodel.UserViewModel

class SplashFragment : Fragment() {
    lateinit var binding : FragmentSplashBinding
    lateinit var userViewModel: UserViewModel
    lateinit var splash : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.dataUser.observe(requireActivity()){
            splash = it.splash
        }
        val sp: Long = 300
        Handler().postDelayed({
            if (splash== "false") {
                Navigation.findNavController(view)
                    .navigate(R.id.action_splashFragment_to_loginFragment)
            }else if (splash == "true")
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment)
        },sp)
    }
}