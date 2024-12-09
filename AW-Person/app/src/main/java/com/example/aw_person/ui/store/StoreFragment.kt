package com.example.aw_person.ui.store

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
import com.example.aw_person.Adapter.StoreAdapter
import com.example.aw_person.databinding.FragmentStoreBinding
import com.example.aw_person.model.Store
import com.example.aw_person.ui.activitys.AddStore

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var storeAdapter: StoreAdapter

    // Maneja el resultado de la actividad con Launch
    private val addEdit = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            storeViewModel.fetchStores()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        storeViewModel = ViewModelProvider(this)[StoreViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa y configura el RecyclerView
        storeAdapter = StoreAdapter { store ->
            showStoreDetail(store) // Llama a este método al hacer clic en un item
        }
        binding.recyclerViewStores.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewStores.adapter = storeAdapter

        // Observa los datos en el ViewModel
        storeViewModel.stores.observe(viewLifecycleOwner) { stores ->
            storeAdapter.updateStores(stores)
        }

        // Obtiene los datos
        storeViewModel.fetchStores()

        // Botón para crear tienda
        binding.createStore.setOnClickListener {
            val intent = Intent(requireContext(), AddStore::class.java)
            addEdit.launch(intent)
        }
    }

    private fun showStoreDetail(store: Store) {
        Log.i("STORE", "Datos" + store)
        val options = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(requireContext())
            .setTitle("Seleccione la opcion a realizar con la tienda ${store.Name}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> updateStore(store)
                    1 -> deleteStore(store)
                }
            }
            .show()
    }

    private fun updateStore(store: Store) {
        val intent = Intent(requireContext(), AddStore::class.java).apply {
            putExtra("STORE_ID", store.BusinessEntityID)
            putExtra("STORE_NAME", store.Name)
            putExtra("STORE_SALESPERSONID", store.SalesPersonID)
        }
        addEdit.launch(intent)
    }

    private fun deleteStore(store: Store) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar tienda: ${store.Name}")
            .setMessage("¿Estás seguro de que deseas eliminar esta tienda?")
            .setPositiveButton("Sí") { _, _ ->
                storeViewModel.deleteStore(store) // Llama al ViewModel para borrar
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}