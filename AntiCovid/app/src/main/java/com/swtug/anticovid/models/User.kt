package com.swtug.anticovid.models

data class User(
    val id: Long = -1,
    val name: String,
    val surname: String,
    val email: String,
    var address: String,
    var secid: String,
    var phonenumber: String,
    val password: String
)

