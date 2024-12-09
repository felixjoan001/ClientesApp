package com.example.aw_person.ui.activitys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.aw_person.databinding.FragmentEditVendorBinding
import com.example.aw_person.ui.home.HomeViewModel

class EditVendorFragment : Fragment() {

    private lateinit var binding: FragmentEditVendorBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var vendorId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditVendorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperar los datos enviados por argumentos
        arguments?.let {
            vendorId = it.getInt("VENDOR_ID", -1) // Se cambia STORE_ID por VENDOR_ID
            binding.editTextVendorName.setText(it.getString("VENDOR_NAME")) // Cambio de STORE_NAME por VENDOR_NAME
            binding.editTextAccountNumber.setText(it.getString("VENDOR_ACCOUNT_NUMBER")) // Cambio de STORE_SALESPERSON_ID por VENDOR_ACCOUNT_NUMBER
            binding.checkBoxPreferredVendor.isChecked = it.getBoolean("PREFERRED_VENDOR", false) // Checkbox para proveedor preferido
            binding.checkBoxActiveFlag.isChecked = it.getBoolean("ACTIVE_FLAG", false) // Checkbox para estado activo
        }
    }
}