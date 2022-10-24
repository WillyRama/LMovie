package com.willyramad.lmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.willyramad.lmovie.model.ResponFilmItem
import com.willyramad.lmovie.service.ApiService
import com.willyramad.lmovie.service.RestFullApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(var api : ApiService) : ViewModel() {
    lateinit var liveDataFilm : MutableLiveData<ResponFilmItem?>

    init {
        liveDataFilm = MutableLiveData()
    }
    fun getAllFilm() : MutableLiveData<ResponFilmItem?>{
        return liveDataFilm
    }
    fun callAllFilm(){
        api.getAllFilm()
            .enqueue(object :Callback<ResponFilmItem>{
                override fun onResponse(
                    call: Call<ResponFilmItem>,
                    response: Response<ResponFilmItem>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    }else{
                        liveDataFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponFilmItem>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }
}