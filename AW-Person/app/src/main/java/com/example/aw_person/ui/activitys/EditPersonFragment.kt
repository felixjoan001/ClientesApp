package com.example.aw_person.ui.activitys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.aw_person.databinding.FragmentEditPersonBinding
import com.example.aw_person.ui.home.HomeViewModel

class EditPersonFragment : Fragment() {

    private lateinit var binding: FragmentEditPersonBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var personId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperar los datos enviados por argumentos
        arguments?.let {
            personId = it.getInt("PERSON_ID", -1)
            binding.editTextFirstName.setText(it.getString("PERSON_FIRSTNAME"))
            binding.editTextLastName.setText(it.getString("PERSON_LASTNAME"))
            binding.editTextPersonType.setText(it.getString("PERSON_PERSONTYPE"))
        }
    }
}