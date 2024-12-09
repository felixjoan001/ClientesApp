package com.example.aw_person.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.aw_person.API.Api
import com.example.aw_person.databinding.ActivityAddVendorBinding
import com.example.aw_person.model.Vendor
import com.example.aw_person.services.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddVendor : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddVendorBinding
    private val service = Api.getClient().create(Service::class.java)

    private var vendorId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Agregar nuevo proveedor"

        vendorId = intent.getIntExtra("VENDOR_ID", 0)

        if (vendorId != 0) {
            getInfoVendor()
        }

        binding.btnSave.setOnClickListener {
            if (vendorId == 0) {
                create()
            } else {
                update()
            }
        }
    }

    private fun getInfoVendor() {
        Log.d("API_CALL", "Iniciando solicitud para vendorId: $vendorId")

        service.getVendorById(vendorId).enqueue(object : Callback<List<Vendor>> {
            override fun onResponse(call: Call<List<Vendor>>, response: Response<List<Vendor>>) {
                Log.d("API_CALL", "Respuesta recibida: ${response.code()}")

                val vendors = response.body()
                if (response.isSuccessful) {
                    Log.d("API_CALL", "La respuesta es exitosa")
                    if (!vendors.isNullOrEmpty()) {
                        val vendor = vendors[0]
                        Log.d("API_CALL", "Vendor obtenido: ${vendor.Name}, AccountNumber: ${vendor.AccountNumber}")
                        binding.etVendorName.setText(vendor.Name)
                        binding.etAccountNumber.setText(vendor.AccountNumber)
                        binding.etCreditRating.setText((vendor.CreditRating.toString()))

                        // Asignar el valor del estado activo del proveedor al CheckBox
                        binding.cbPreferredVendor.isChecked = vendor.PreferredVendorStatus // Asegúrate de que VendorActive sea un Boolean
                        binding.cbActiveFlag.isChecked = vendor.ActiveFlag
                    } else {
                        Log.d("API_CALL", "No se encontraron proveedores")
                    }
                } else {
                    Log.e("API_CALL", "Error en la respuesta: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Vendor>>, t: Throwable) {
                Log.e("API_CALL", "Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun create() {
        val vendor = Vendor(
            Name = binding.etVendorName.text.toString(),
            AccountNumber = binding.etAccountNumber.text.toString(),
            CreditRating = binding.etCreditRating.text.toString().toIntOrNull() ?: 0, // Manejar valores nulos o inválidos
            PreferredVendorStatus = binding.cbPreferredVendor.isChecked,
            ActiveFlag = binding.cbActiveFlag.isChecked
        )

        service.createVendor(vendor).enqueue(object : Callback<Vendor> {
            override fun onResponse(call: Call<Vendor>, response: Response<Vendor>) {
                if (response.isSuccessful) {
                    Log.i("CREATE", "Vendor creado correctamente: ${response.body()}")
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Log.e("CREATE", "Error al crear vendor: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Vendor>, t: Throwable) {
                Log.e("CREATE", "Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun update() {
        val vendor = Vendor(
            BusinessEntityID = vendorId,
            Name = binding.etVendorName.text.toString(),
            AccountNumber = binding.etAccountNumber.text.toString(),
            CreditRating = binding.etCreditRating.text.toString().toIntOrNull() ?: 0, // Manejar valores nulos o inválidos
            PreferredVendorStatus = binding.cbPreferredVendor.isChecked,
            ActiveFlag = binding.cbActiveFlag.isChecked
        )

        service.updateVendor(vendorId, vendor).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i("UPDATE", "Vendor actualizado correctamente")
                    val intent = Intent()
                    intent.putExtra("IS_UPDATE", true)
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Log.e("UPDATE", "Error al actualizar vendor: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("UPDATE", "Error en la solicitud: ${t.message}")
            }
        })
    }
}