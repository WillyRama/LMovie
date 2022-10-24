package com.willyramad.lmovie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.willyramad.lmovie.datastore.UserPreferencesRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserPreferencesRepository(application)
    val dataUser = repo.readProto.asLiveData()

    fun editData(nama : String, email : String, username : String, password : String, splash : String)= viewModelScope.launch{
        repo.saveData(nama,email,username,password,splash)

    }

    fun clearData() = viewModelScope.launch {
        repo.deleteData()
    }
    fun editSplash(splash: String) = viewModelScope.launch{
        repo.Ssplash(splash)
    }
}