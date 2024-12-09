package com.example.aw_person.ui.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aw_person.API.Api
import com.example.aw_person.model.Store
import com.example.aw_person.services.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {

    private val _stores = MutableLiveData<List<Store>>()
    val stores: LiveData<List<Store>> get() = _stores
    private val apiService: Service = Api.getClient().create(Service::class.java)

    fun fetchStores() {
        apiService.getAllStores().enqueue(object : Callback<List<Store>> {
            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.isSuccessful) {
                    val storeList = response.body() ?: emptyList()
                    _stores.value = storeList
                } else {
                    Log.e("API_ERROR", "Error al obtener tiendas: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Log.e("API_CALL", "Fallo en la llamada a la API: ${t.message}")
            }
        })
    }

    fun deleteStore(store: Store) {
        val id = store.BusinessEntityID
        Log.i("STORE_ID", id.toString())
        if (id != null) {
            apiService.deleteStore(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchStores() // Actualiza la lista después de borrar
                    } else {
                        Log.e(
                            "API_ERROR",
                            "Error al eliminar tienda ${store.Name}: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(
                        "API_CALL",
                        "Fallo al eliminar tienda ${store.Name}: ${t.message ?: "Error desconocido"}"
                    )
                }
            })
        } else {
            Log.e("VALIDATION_ERROR", "El ID de la tienda es nulo.")
        }
    }
}