package com.example.aw_person.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    //Cuando utilizas un emulador, el host de tu máquina local no es accesible directamente como
    // 127.0.0.1. Android emula una red virtual y mapea 10.0.2.2 a tu máquina local.

    //cmd->ipconfig->usar ipv4

    //si se cambia la ip aqui tambien se debe modificar en el xml-> network_security_config.xml

    //ip de mi pc principal
    private const val BASE_URL = "http://192.168.100.2:3000"

    //ip 2dapc
    //private const val BASE_URL = "http://192.168.3.205:5000"

    fun getClient():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}