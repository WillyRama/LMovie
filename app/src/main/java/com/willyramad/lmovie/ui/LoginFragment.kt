package com.willyramad.lmovie.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.willyramad.lmovie.R
import com.willyramad.lmovie.databinding.FragmentLoginBinding
import com.willyramad.lmovie.viewmodel.UserViewModel

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var user : String
    lateinit var Pass : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            login(view)
        }
        binding.btnRegis.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
        userViewModel.dataUser.observe(requireActivity()){
            user = it.username
            Pass = it.pass
        }
    }
    fun login(view: View){
        binding.btnLogin.setOnClickListener {
            val edUser = binding.user.editText.toString()
            val edPass = binding.pass.editText.toString()
           if (edUser.isEmpty() && edPass.isEmpty() ){
               Toast.makeText(context, "Isi kolom terlebih dahulu", Toast.LENGTH_SHORT).show()
           }else if ( edUser == user && edPass == Pass)
               userViewModel.editSplash("true")
               Toast.makeText(context, "berhasil login", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)

        }
    }
}
//if (edUser.isEmpty() && edPass.isEmpty()) {
//    Toast.makeText(requireActivity(), "Kolom Masih Kosong", Toast.LENGTH_SHORT).show()
//}else{
//    if (edUser == user &&  edPass == Pass){
//        userViewModel.editSplash("true")
//        Toast.makeText(requireActivity(), "Login Berhasil", Toast.LENGTH_SHORT).show()
//        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
//    }else{
//        Toast.makeText(requireActivity(), "Login gagal", Toast.LENGTH_SHORT).show()
//        Navigation.findNavController(view).navigate(R.id.loginFragment)
//    }
//}