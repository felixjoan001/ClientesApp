package com.example.aw_person.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aw_person.databinding.ItemPersonBinding
import com.example.aw_person.model.Person


class PersonAdapter(private val onItemClicked: (Person)->Unit) :
        RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(){
        private var persons: List<Person> = emptyList()

        @SuppressLint("NotifyDataSetChanged")
        fun updatePersons(persons: List<Person>){
                this.persons = persons

                notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
        ): PersonAdapter.PersonViewHolder {
                val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return PersonViewHolder(binding)
        }

        override fun onBindViewHolder(
                holder: PersonAdapter.PersonViewHolder,
                position: Int,
        ) {
                holder.bind(persons[position])



        }

        override fun getItemCount(): Int = persons.size

        inner class PersonViewHolder(private val binding: ItemPersonBinding) :
                RecyclerView.ViewHolder(binding.root) {
                private lateinit var person: Person

                init {
                        binding.root.setOnClickListener {
                                Log.d("PersonAdapter", "Clic detectado en: ${person.FirstName} ${person.LastName}")
                                onItemClicked(person) // Llama al listener con la persona actual
                        }
                }

                fun bind(person: Person) {
                        this.person = person
                        binding.tvPersonName.text = "${person.FirstName} ${person.LastName}"
                }

        }

}

