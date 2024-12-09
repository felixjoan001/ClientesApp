package com.example.aw_person.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.aw_person.API.Api
import com.example.aw_person.databinding.ActivityAddStoreBinding
import com.example.aw_person.model.Store
import com.example.aw_person.services.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStore : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddStoreBinding
    private val service = Api.getClient().create(Service::class.java)

    private var storeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Agregar nueva tienda"

        storeId = intent.getIntExtra("STORE_ID", 0)

        if (storeId != 0) {
            getInfoStore()
        }

        binding.btnSave.setOnClickListener {
            if (storeId == 0) {
                create()
            } else {
                update()
            }
        }
    }

    private fun getInfoStore() {
        Log.d("API_CALL", "Iniciando solicitud para storeId: $storeId")

        service.getStoreById(storeId).enqueue(object : Callback<List<Store>> {
            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                Log.d("API_CALL", "Respuesta recibida: ${response.code()}")

                val stores = response.body()
                if (response.isSuccessful) {
                    Log.d("API_CALL", "La respuesta es exitosa")
                    if (!stores.isNullOrEmpty()) {
                        val store = stores[0]
                        Log.d("API_CALL", "Store obtenido: ${store.Name}, SalesPersonID: ${store.SalesPersonID}")
                        binding.etStoreName.setText(store.Name)
                        binding.etSalesPersonID.setText(store.SalesPersonID)
                    } else {
                        Log.d("API_CALL", "No se encontraron tiendas")
                    }
                } else {
                    Log.e("API_CALL", "Error en la respuesta: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Log.e("API_CALL", "Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun create() {
        val store = Store(
            Name = binding.etStoreName.text.toString(),
            SalesPersonID = binding.etSalesPersonID.text.toString()
        )

        service.createStore(store).enqueue(object : Callback<Store> {
            override fun onResponse(call: Call<Store>, response: Response<Store>) {
                if (response.isSuccessful) {
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }
                Log.i("RESPONSE", "Success: ${response.isSuccessful}, Code: ${response.code()}")
            }

            override fun onFailure(call: Call<Store>, t: Throwable) {
                Log.e("API_CALL", "Error creating store: ${t.message}")
            }
        })
    }

    private fun update() {
        val store = Store(
            BusinessEntityID = storeId,
            Name = binding.etStoreName.text.toString(),
            SalesPersonID = binding.etSalesPersonID.text.toString()
        )

        service.updateStore(storeId, store).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val intent = Intent()
                    intent.putExtra("IS_UPDATE", true)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API_CALL", "Error updating store: ${t.message}")
            }
        })
    }
}