package com.example.aw_person.ui.vendor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aw_person.API.Api
import com.example.aw_person.model.Vendor // Se cambia Store por Vendor
import com.example.aw_person.services.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorViewModel : ViewModel() {

    private val _vendors = MutableLiveData<List<Vendor>>() // Cambiar Store por Vendor
    val vendors: LiveData<List<Vendor>> get() = _vendors
    private val apiService: Service = Api.getClient().create(Service::class.java)

    // Método para obtener la lista de proveedores
    fun fetchVendors() {
        apiService.getAllVendors().enqueue(object : Callback<List<Vendor>> { // Cambiar Stores por Vendors
            override fun onResponse(call: Call<List<Vendor>>, response: Response<List<Vendor>>) {
                if (response.isSuccessful) {
                    val vendorList = response.body() ?: emptyList() // Lista de proveedores
                    _vendors.value = vendorList
                } else {
                    Log.e("API_ERROR", "Error al obtener proveedores: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Vendor>>, t: Throwable) {
                Log.e("API_CALL", "Fallo en la llamada a la API: ${t.message}")
            }
        })
    }

    // Método para eliminar un proveedor
    fun deleteVendor(vendor: Vendor) {
        val id = vendor.BusinessEntityID
        Log.i("VENDOR_ID", id.toString())
        if (id != null) {
            apiService.deleteVendor(id).enqueue(object : Callback<Void> { // Cambiar Store por Vendor
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchVendors() // Actualiza la lista después de eliminar
                    } else {
                        Log.e(
                            "API_ERROR",
                            "Error al eliminar proveedor ${vendor.Name}: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(
                        "API_CALL",
                        "Fallo al eliminar proveedor ${vendor.Name}: ${t.message ?: "Error desconocido"}"
                    )
                }
            })
        } else {
            Log.e("VALIDATION_ERROR", "El ID del proveedor es nulo.")
        }
    }
}