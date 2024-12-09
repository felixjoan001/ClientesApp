package com.example.aw_person.ui.vendor

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aw_person.Adapter.VendorAdapter
import com.example.aw_person.databinding.FragmentVendorBinding
import com.example.aw_person.model.Vendor
import com.example.aw_person.ui.activitys.AddVendor

class VendorFragment : Fragment() {

    private var _binding: FragmentVendorBinding? = null
    private val binding get() = _binding!!
    private lateinit var vendorViewModel: VendorViewModel
    private lateinit var vendorAdapter: VendorAdapter

    // Maneja el resultado de la actividad
    private val addEdit = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            vendorViewModel.fetchVendors()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVendorBinding.inflate(inflater, container, false)
        vendorViewModel = ViewModelProvider(this)[VendorViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa y configura el RecyclerView
        vendorAdapter = VendorAdapter { vendor ->
            showVendorDetail(vendor) // Llama a este método al hacer clic en un item
        }
        binding.recyclerViewVendors.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewVendors.adapter = vendorAdapter

        // Observa los datos en el ViewModel
        vendorViewModel.vendors.observe(viewLifecycleOwner) { vendors ->
            vendorAdapter.updateVendors(vendors)
        }

        // Obtiene los datos
        vendorViewModel.fetchVendors()

        // Botón para crear proveedor
        binding.createVendor.setOnClickListener {
            val intent = Intent(requireContext(), AddVendor::class.java)
            addEdit.launch(intent)
        }
    }

    private fun showVendorDetail(vendor: Vendor) {
        Log.i("VENDOR", "Datos: $vendor")
        val options = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(requireContext())
            .setTitle("Seleccione la opción a realizar con el proveedor ${vendor.Name}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> updateVendor(vendor)
                    1 -> deleteVendor(vendor)
                }
            }
            .show()
    }

    private fun updateVendor(vendor: Vendor) {
        val intent = Intent(requireContext(), AddVendor::class.java).apply {
            putExtra("VENDOR_ID", vendor.BusinessEntityID)
            putExtra("VENDOR_NAME", vendor.Name)
            putExtra("ACCOUNT_NUMBER", vendor.AccountNumber)
            putExtra("PREFERRED_VENDOR", vendor.PreferredVendorStatus)
            putExtra("ACTIVE_FLAG", vendor.ActiveFlag)
        }
        addEdit.launch(intent)
    }

    private fun deleteVendor(vendor: Vendor) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar proveedor: ${vendor.Name}")
            .setMessage("¿Estás seguro de que deseas eliminar este proveedor?")
            .setPositiveButton("Sí") { _, _ ->
                vendorViewModel.deleteVendor(vendor) // Llama al ViewModel para borrar
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}