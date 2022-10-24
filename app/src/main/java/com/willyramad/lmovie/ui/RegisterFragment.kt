package com.willyramad.lmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.willyramad.lmovie.R
import com.willyramad.lmovie.databinding.FragmentRegisterBinding
import com.willyramad.lmovie.viewmodel.UserViewModel

class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding
    lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        regis(view)

    }
    fun regis(view: View){
        val edNama = binding.nama.text.toString()
        val edEmail = binding.email.text.toString()
        val edUser = binding.user.text.toString()
        val edPass = binding.pass.text.toString()
        val edKpass = binding.pass.text.toString()

        binding.btnregis.setOnClickListener {
            if (edPass == edKpass){
                userViewModel.editData(edNama,edEmail,edUser,edPass, "false")
                Toast.makeText(requireActivity(), "Anda berhasil mendaftar", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
            }else{
                Toast.makeText(requireActivity(), "Password salah", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.registerFragment)

            }
        }
    }
}