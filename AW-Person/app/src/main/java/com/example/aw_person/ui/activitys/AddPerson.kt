package com.example.aw_person.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.aw_person.API.Api
import com.example.aw_person.databinding.ActivityAddPersonBinding

import com.example.aw_person.services.Service
import com.example.aw_person.model.Person
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

class AddPerson : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddPersonBinding
    private val service = Api.getClient().create(Service::class.java)

    private var personId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Agregar nueva persona"

        personId = intent.getIntExtra("PERSON_ID", 0)
        if(personId !=0){
            getInfoPerson()
        }

        binding.btnSave.setOnClickListener{
            if(personId ==0){
                create()
            }else{
                update()
            }
        }
    }

    private fun getInfoPerson(){
        service.getPersonById(personId).enqueue(object :Callback<List<Person>>{
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                val persons = response.body()

                if(response.isSuccessful){
                    if(!persons.isNullOrEmpty()){
                        val person = persons[0]
                        binding.etFirstName.setText(person.FirstName)
                        binding.etLastName.setText(person.LastName)
                        binding.etPersonType.setText(person.PersonType)
                    }
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun create() {
        val person = Person(
            PersonType = binding.etPersonType.text.toString(),
            FirstName = binding.etFirstName.text.toString(),
            LastName = binding.etLastName.text.toString()
        )

        service.createPerson(person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val intent = Intent()
                    setResult(RESULT_OK,intent)
                    finish()
                }
                Log.i("RESPONSE", "Success: ${response.isSuccessful}, Code: ${response.code()}")
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun update() {
        val person = Person(
            BusinessEntityID = personId,
            PersonType = binding.etPersonType.text.toString(),
            FirstName = binding.etFirstName.text.toString(),
            LastName = binding.etLastName.text.toString()

        )

        service.updatePerson(personId, person).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val intent = Intent()
                    intent.putExtra("IS_UPDATE", true)
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle error
            }
        })
    }

}