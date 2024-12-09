package com.example.aw_person.ui.activitys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.aw_person.databinding.FragmentEditStoreBinding
import com.example.aw_person.ui.home.HomeViewModel

class EditStoreFragment : Fragment() {

    private lateinit var binding: FragmentEditStoreBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var storeId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperar los datos enviados por argumentos
        arguments?.let {
            storeId = it.getInt("STORE_ID", -1) // Se cambia PERSON_ID por STORE_ID
            binding.editTextStoreName.setText(it.getString("STORE_NAME")) // Cambio de PERSON_FIRSTNAME por STORE_NAME
            binding.editTextSalesPersonID.setText(it.getString("STORE_SALESPERSON_ID")) // Cambio de PERSON_LASTNAME por STORE_SALESPERSON_ID
        }
    }
}