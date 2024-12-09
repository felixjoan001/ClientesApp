package com.example.aw_person.model

data class Vendor(
    val BusinessEntityID: Int?=null,
    val Name: String,
    val AccountNumber: String,
    val CreditRating: Int,
    val PreferredVendorStatus: Boolean,
    val ActiveFlag: Boolean
)