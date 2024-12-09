package com.example.aw_person.ui.home

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aw_person.databinding.FragmentPersonBinding
import com.example.aw_person.Adapter.PersonAdapter
import com.example.aw_person.model.Person
import com.example.aw_person.ui.activitys.AddPerson

class HomeFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var personAdapter: PersonAdapter

    // Maneja el resultado de la actividad con Launch
    private val addEdit = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            homeViewModel.fetchPersons()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa y configura el RecyclerView
        personAdapter = PersonAdapter { person ->
            showPersonDetail(person) // Llama a este método al hacer clic en un item
        }
        binding.recyclerViewPersons.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPersons.adapter = personAdapter

        // Observa los datos en el ViewModel
        homeViewModel.persons.observe(viewLifecycleOwner) { persons ->
            personAdapter.updatePersons(persons)
        }

        // Obtiene los datos
        homeViewModel.fetchPersons()

        // Botón para crear persona
        binding.create.setOnClickListener {
            val intent = Intent(requireContext(), AddPerson::class.java)
            addEdit.launch(intent)
        }

    }

    private fun showPersonDetail(person: Person) {
        val options = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(requireContext())
            .setTitle("Acciones para ${person.FirstName}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> updatePerson(person)
                    1 -> deletePerson(person)
                }
            }
            .show()
    }

    private fun updatePerson(person: Person) {
        val intent = Intent(requireContext(), AddPerson::class.java).apply {
            putExtra("PERSON_ID", person.BusinessEntityID)
            putExtra("PERSON_FIRSTNAME", person.FirstName)
            putExtra("PERSON_LASTNAME", person.LastName)
            putExtra("PERSON_PERSONTYPE", person.PersonType)
        }
        addEdit.launch(intent)
    }

    private fun deletePerson(person: Person) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar a ${person.FirstName}")
            .setMessage("¿Estás seguro de que deseas eliminar esta persona?")
            .setPositiveButton("Sí") { _, _ ->

                homeViewModel.deletePerson(person) // Llama al ViewModel para borrar
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}