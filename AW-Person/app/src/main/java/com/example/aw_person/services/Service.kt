package com.example.aw_person.services
import com.example.aw_person.model.Person
import com.example.aw_person.model.Store
import com .example.aw_person.model.Vendor

import retrofit2.Call
import retrofit2.http.*

interface Service {
    // PERSONS
    @GET("/api/person")
    fun getAllPersons(): Call<List<Person>>

    @GET("/api/person/{id}")
    fun getPersonById(@Path("id") id: Int): Call<List<Person>>

    @POST("/api/create/person")
    fun createPerson(@Body person:Person): Call<Person>

    @PUT("/api/person/update/{id}")
    fun updatePerson(@Path("id") id:Int, @Body person:Person): Call<Void>

    @DELETE("/api/person/delete/{id}")
    fun deletePerson(@Path("id") id:Int): Call<Void >

    //Stores
    @GET ("/api/store")
    fun getAllStores(): Call<List<Store>>

    @GET("/api/store/{id}")
    fun getStoreById(@Path("id") id: Int): Call<List<Store>>

    @POST("/api/create/store")
    fun createStore(@Body store:Store): Call<Store>

    @PUT("/api/store/update/{id}")
    fun updateStore(@Path("id") id:Int, @Body store:Store): Call<Void>

    @DELETE("/api/store/delete/{id}")
    fun deleteStore(@Path("id") id: Int): Call<Void>

    //Vendor

    @GET ("/api/vendor")
    fun getAllVendors(): Call<List<Vendor>>

    @GET ("/api/vendor/{id}")
    fun getVendorById(@Path("id") id:Int): Call<List<Vendor>>

    @POST("/api/create/vendor")
    fun createVendor(@Body vendor:Vendor): Call<Vendor>

    @PUT("/api/vendor/update/{id}")
    fun updateVendor(@Path("id") id:Int, @Body vendor:Vendor): Call<Void>

    @DELETE("/api/delete/vendor/{id}")
    fun deleteVendor(@Path("id") id:Int): Call<Void>



}