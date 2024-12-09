package com.example.aw_person.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aw_person.API.Api
import com.example.aw_person.model.Person
import com.example.aw_person.services.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons
    private val apiService: Service = Api.getClient().create(Service::class.java)



    fun fetchPersons() {
        apiService.getAllPersons().enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                if (response.isSuccessful) {
                    val personsList = response.body() ?: emptyList()

                    _persons.value = personsList
                } else {
                    Log.i("ERROR", "Error al solicitar a la API: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                Log.e("API_CALL", "Fallo en la llamada a la API: ${t.message}")
            }
        })
    }

    fun deletePerson(person: Person) {
        val id = person.BusinessEntityID
        Log.i("VALOR_ID", id.toString())
        if (id != null) {
            apiService.deletePerson(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchPersons() // Actualiza la lista después de borrar
                    } else {
                        Log.e(
                            "API_ERROR",
                            "Error al eliminar a ${person.FirstName}: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(
                        "API_CALL",
                        "Fallo al eliminar a ${person.FirstName}: ${t.message ?: "Error desconocido"}"
                    )
                }
            })
        } else {
            Log.e("VALIDATION_ERROR", "El ID de la persona es nulo.")
        }
    }
}