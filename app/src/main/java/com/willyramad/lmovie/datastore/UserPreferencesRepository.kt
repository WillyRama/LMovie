package com.willyramad.lmovie.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.willyramad.lmovie.UserProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class UserPreferencesRepository(private val context: Context) {
    //membuat data store
    private val Context.userPreferencesStore: DataStore<UserProto> by dataStore(
        fileName = "userData",
        serializer = UserPreferencesSerializer
    )

    //simpan data ke proto
    suspend fun saveData(nama : String, email : String, username : String, password : String, splash : String) {
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setNama(nama).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setEmail(email).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setUsername(username).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setPass(password).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setSplash(splash).build()
        }
    }

    //hapus data proto
    suspend fun deleteData(){
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearNama().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearEmail().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearUsername().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearPass().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearSplash().build()
        }
    }


    //baca data proto
    val readProto: Flow<UserProto> = context.userPreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("tag", "Error reading sort order preferences.", exception)
                emit(UserProto.getDefaultInstance())
            } else {
                throw exception
            }
        }
    //set session
    suspend fun Ssplash(splash : String) {
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setSplash(splash).build()
        }
    }
}